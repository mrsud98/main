from flask import Flask, request, send_file, jsonify
from flask_jwt_extended import JWTManager, jwt_required, create_access_token
import os

app = Flask(__name__)
app.config['JWT_SECRET_KEY'] = 'your-secret-key'  # Replace with a strong secret key
jwt = JWTManager(app)

@app.route('/')
def hello():
    return "You are enter in new world of import"
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


@app.route('/get_token', methods=['POST'])
def get_token():
    username = request.json.get('username')
    password = request.json.get('password')
    # Add your authentication logic here (e.g., check credentials)
    if username == 'your_username' and password == 'your_password':
        access_token = create_access_token(identity=username)
        return {'access_token': access_token}
    else:
        return {'message': 'Invalid credentials'}, 401

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

@app.route('/download/<filename>', methods=['GET'])
@jwt_required()
def download_file(filename):
    if os.path.isfile(f'uploads/{filename}'):
        return send_file(f'uploads/{filename}', as_attachment=True)
    else:
        return "File not found"

if __name__ == '__main__':
    app.run()
