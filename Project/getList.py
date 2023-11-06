import requests

import jwt
import datetime

# Define the payload (claims) you want to include in the JWT
payload = {
    "sub": "1234567890",  # Subject (typically a user ID)
    "name": "John Doe",
    "exp": datetime.datetime(2023, 12, 31, 23, 59, 59)
}

# Define your secret key (you should keep this secret)
secret_key = "your_secret_key"


# Create the JWT by encoding the payload with the secret key
token = jwt.encode(payload, secret_key, algorithm="HS256")

print(token)


# URL of the Flask API endpoint to list files
api_url = 'http://localhost:5000/list-files'  # Replace with your API URL
# jwt_token = 'token'

headers = {
    'Authorization': f'Bearer {token}'
}


try:
    response = requests.get(api_url)

    if response.status_code == 200:
        file_list = response.json()
        print("List of files:")
        for file_name in file_list:
            print(file_name)
    else:
        print(f"Failed to retrieve the list of files with status code {response.status_code}")

except Exception as e:
    print(f"An error occurred: {str(e)}")
