from flask import Flask, request, jsonify, render_template
import jwt
import datetime
from functools import wraps

app = Flask(__name__)
app.config['SECRET_KEY'] = 'your-secret-key'  # Change this to a secret key of your choice

# Dummy user data (You should use a database in a real app)
users = {
    'user1': 'password1',
    'user2': 'password2',
}

# Decorator to protect routes that require authentication
def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = request.headers.get('Authorization')

        if not token:
            return jsonify({'message': 'Token is missing.'}), 401

        if not token.startswith('Bearer '):
            return jsonify({'message': 'Invalid token format.'}), 401

        token = token.split(' ')[1]

        try:
            data = jwt.decode(token, app.config['SECRET_KEY'])
        except jwt.ExpiredSignatureError:
            return jsonify({'message': 'Token has expired.'}), 401
        except jwt.InvalidTokenError:
            return jsonify({'message': 'Invalid token.'}), 401

        return f(*args, **kwargs)

    return decorated



@app.route("/")
def show_table():
    return render_template("index.html", message="")


# Registration endpoint
@app.route('/register', methods=['POST'])
def register():

    username = "user8"
    password = "password1"

    if username in users:
        return jsonify({'message': 'User already exists.'})

    users[username] = password
    return jsonify({'message': 'User registered successfully.'})

# Login endpoint
@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    username = data.get('username')
    password = data.get('password')

    if username in users and users[username] == password:
        token = jwt.encode({
            'user': username,
            'exp': datetime.datetime.utcnow() + datetime.timedelta(minutes=30)
        }, app.config['SECRET_KEY'])
        return jsonify({'token': token})
    else:
        return jsonify({'message': 'Invalid credentials.'}), 401

# Protected route
@app.route('/protected', methods=['GET'])
@token_required
def protected():
    return jsonify({'message': 'This is a protected route.'})

if __name__ == '__main__':
    app.run(debug=True)
