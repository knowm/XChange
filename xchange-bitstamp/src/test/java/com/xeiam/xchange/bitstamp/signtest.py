import hashlib
import hmac

api_key="a4SDmpl9s6xWJS5fkKRT6yn41vXuY0AM"
nonce="1380907407152"
client_id = "34387"
API_SECRET = "sisJixU6Xd0d1yr6w02EHCb9UwYzTNuj"
message = nonce + client_id + api_key
signature = hmac.new(API_SECRET, msg=message, digestmod=hashlib.sha256).hexdigest().upper()
print(signature)

# The correct signature in this case is DBD99750DC6112FCA0A1BBBB5014B9DEA8F9CD626A50675BB18CCDBA8E0EFC0F