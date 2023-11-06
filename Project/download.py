import os
import requests

# URL of the Flask API endpoint to download files
api_url = 'http://localhost:5000/download-file'  # Replace with your API URL

# Specify the file name you want to download
file_name = 'new.csv'  # Replace with the desired file name

# Define the folder for downloaded files
download_folder = 'Downloaded_files'

# Create the download folder if it doesn't exist
if not os.path.exists(download_folder):
    os.makedirs(download_folder)

try:
    response = requests.get(api_url, params={'file_name': file_name})

    if response.status_code == 200:
        download_path = os.path.join(download_folder, file_name)
        with open(download_path, 'wb') as file:
            file.write(response.content)
        print(f"File '{file_name}' downloaded and saved to '{download_path}'.")
    else:
        print(f"Failed to download the file with status code {response.status_code}")

except Exception as e:
    print(f"An error occurred: {str(e)}")
