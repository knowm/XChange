from eth_account import Account
from eth_account.messages import encode_structured_data, _hash_eip191_message
from pprint import pprint

# Replace this with your Ethereum private key
private_key = '09093d55d404c51871cc12a73fc482a245bb066d101d1ac840d73ee534cee4b9'


def hex_to_bytes32(hex_string):
    if hex_string.startswith("0x"):
        hex_string = hex_string[2:]
    data_bytes = bytes.fromhex(hex_string)
    padded_data = b'\x00' * (32 - len(data_bytes)) + data_bytes
    return padded_data


sender = hex_to_bytes32('0x841fe4876763357975d60da128d8a54bb045d76a64656661756c740000000000')

# EIP-712 Typed Data
typed_data = {
    'types': {
        'EIP712Domain': [
            {'name': 'name', 'type': 'string'},
            {'name': 'version', 'type': 'string'},
            {'name': 'chainId', 'type': 'uint256'},
            {'name': 'verifyingContract', 'type': 'address'}
        ],
        'Order': [
            {'name': 'sender', 'type': 'bytes32'},
            {'name': 'priceX18', 'type': 'int128'},
            {'name': 'amount', 'type': 'int128'},
            {'name': 'expiration', 'type': 'uint64'},
            {'name': 'nonce', 'type': 'uint64'},
        ],
    },
    'primaryType': 'Order',
    'domain': {
        'name': 'Vertex',
        'version': '0.0.1',
        'chainId': 421613,
        'verifyingContract': '0xf03f457a30e598d5020164a339727ef40f2b8fbc'
    },
    'message': {
        'sender': hex_to_bytes32('0x841fe4876763357975d60da128d8a54bb045d76a64656661756c740000000000'),
        'priceX18': 28898000000000000000000,
        'amount': -10000000000000000,
        'expiration': 4611687701117784255,
        'nonce': 1764428860167815857,
    },
}

typed_data2 = {
    'types': {
        'EIP712Domain': [
            {'name': 'name', 'type': 'string'},
            {'name': 'version', 'type': 'string'},
            {'name': 'chainId', 'type': 'uint256'},
            {'name': 'verifyingContract', 'type': 'address'}
        ],
        'Cancellation': [
            {'name': 'sender', 'type': 'bytes32'},
            {'name': 'productIds', 'type': 'uint32[]'},
            {'name': 'digests', 'type': 'bytes32[]'},
            {'name': 'nonce', 'type': 'uint64'},
        ],
    },
    'primaryType': 'Cancellation',
    'domain': {
        'name': 'Vertex',
        'version': '0.0.1',
        'chainId': 421613,
        'verifyingContract': '0xbf16e41fb4ac9922545bfc1500f67064dc2dcc3b'
    },
    'message': {
        'sender': hex_to_bytes32('0x841fe4876763357975d60da128d8a54bb045d76a64656661756c740000000000'),
        'productIds': [4],
        'digests': [hex_to_bytes32('0x51ba8762bc5f77957a4e896dba34e17b553b872c618ffb83dba54878796f2821')],
        'nonce': 1,
    },
}


def sign_typed_data(typed_data, private_key):
    account: Account = Account.from_key(private_key)
    encoded_data = encode_structured_data(typed_data)
    digest = _hash_eip191_message(encoded_data)
    typed_data_hash = account.sign_message(encoded_data)
    return typed_data_hash.signature.hex(), digest.hex()


pprint(typed_data)
signature, digest = sign_typed_data(typed_data, private_key)
print("Signature", signature)
print("digest", digest)

pprint(typed_data2)
signature, digest = sign_typed_data(typed_data2, private_key)
print("Signature", signature)
print("digest", digest)
