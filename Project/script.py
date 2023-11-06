import os
import requests



# URL of the Flask API endpoint for file upload
api_url = 'http://localhost:5000/upload'  # Replace with your API URL



# Replace 'your_file_path' with the actual path to the file you want to upload
file_path = r'D:\csv files\StandradCSV_1D_Data_E7\StandradCSV_1D_Data_E4.csv'



try:
    # Use os.path.basename to extract the file name from the path
    file_name = os.path.basename(file_path)
    with open(file_path, 'rb') as file:
        # Create a dictionary to specify the file data
        files = {'file': (file_name, file)}

        # Send a POST request with the file
        response = requests.post(api_url, files=files)

        # Check the response status
        if response.status_code == 200:
            print("File uploaded successfully.")
        else:
            print(f"File upload failed with status code {response.status_code}")

except Exception as e:
    print(f"An error occurred: {str(e)}")


    
