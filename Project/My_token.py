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
