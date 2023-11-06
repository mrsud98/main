from flask import Flask, request, send_file, render_template, jsonify
import os

app = Flask(__name__)

# Set the upload folder
app.config['UPLOAD_FOLDER'] = 'uploads'

@app.route("/")
def show_table():
    return render_template("index.html", message="")


@app.route('/uploads', methods=['POST'])
def uploads():


    if request.method == 'POST':
        f = request.files['file']
        f.save("./uploads")
        return render_template("index.html", message="file uploaded")

@app.route('/download-file', methods=['GET'])
def download_file():
    file_name = request.args.get('file_name')
    if file_name:
        file_path = f'uploads/{file_name}'  # Specify the path to your uploaded files
        return send_file(file_path, as_attachment=True)
    else:
        return 'File name not provided', 400

@app.route('/upload', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return 'No file part'
    file = request.files['file']
    if file.filename == '':
        return 'No selected file'
    if file:
        file.save('uploads/' + file.filename)  # Save the file to a directory
        return 'File uploaded successfully'


@app.route('/list-files', methods=['GET'])
def list_files():
    directory_path = 'uploads'  # Specify the directory where your uploaded files are stored

    

    file_list = [f for f in os.listdir(directory_path) if os.path.isfile(os.path.join(directory_path, f))]
    return jsonify(file_list)

if __name__ == '__main__':
    app.run(debug=True)
