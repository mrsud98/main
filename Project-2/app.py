from flask import Flask, request, jsonify
from flask_jwt_extended import JWTManager, jwt_required, create_access_token

app = Flask(__name__)
app.config['JWT_SECRET_KEY'] = 'your-secret-key'  # Change this to a secure random key

jwt = JWTManager(app)

# Simulated user data (you should replace this with a database)
users = {'your_username': 'your_password'}

@app.route("/")
def m1():
    return("client side url")

@app.route('/register', methods=['POST'])
def register():
    data = request.get_json()
    username = data.get('username')
    password = data.get('password')

    if username and password:
        if username not in users:
            users[username] = password
            return jsonify(message='Registration successful')
        else:

            return jsonify(message='Username already exists'), 400
    else:
        return jsonify(message='Invalid request'), 400

@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    username = data.get('username')
    password = data.get('password')

    if username in users and users[username] == password:
        access_token = create_access_token(identity=username)
        return jsonify(access_token=access_token)
    else:
        return jsonify(message='Invalid credentials'), 401


@app.route('/upload', methods=['POST'])
@jwt_required()
def upload_file():
    if 'file' not in request.files:
        return "No file part"

    file = request.files['file']

    if file.filename == '':
        return "No selected file"

    # Save the uploaded file to a folder (e.g., 'uploads')
    file.save('uploads/' + file.filename)

    return "File uploaded successfully"

@app.route('/download', methods=['GET'])


@app.route('/protected', methods=['GET'])
@jwt_required()
def protected():
    return jsonify(message='You have access to this protected route!')

if __name__ == '__main__':
    app.run(debug=True)
