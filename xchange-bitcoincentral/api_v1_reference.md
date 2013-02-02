API v1 Documentation
====================

This document describes the Bitcoin-Central.net v1 API.

# General API description

## Authentication

Authentication is currently done as previously with `HTTP Basic`. The regular username and password should be used. This is deprecated and will soon be replaced with OAuth2.

Calls that require authentication are marked with "A" in the call description title.

## Base URL

The base URL for all calls is `https://bitcoin-central.net`. A complete URL would look like this `https://bitcoin-central.net/api/v1/quotes/3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d`.

## Formats and required HTTP request headers

The API will only answer with JSON or empty responses. It expects parameters to be passed in JSON with the correct `Content-Type: application/json` being set.

## Rate-limit

API calls are rate-limited by IP to 5000 calls per day. Information about the status of the limit can be found in the `X-RateLimit-Limit` and `X-RateLimit-Remaining` HTTP headers.

**Example response with rate-limit headers**

    HTTP/1.1 200  
    Content-Type: application/json; charset=utf-8
    X-Ratelimit-Limit: 5000
    X-Ratelimit-Remaining: 4982
    Date: Wed, 30 Jan 2013 12:08:58 GMT


## Pagination

Some API calls returning collections may be paginated. In this case the call description title mentions it.

Calls that return paginated data are marked with "P" in the call description title.

### HTTP response header

Calls that return paginated collections will add a `Pagination` HTT header to the response. It will contain a pagination meta-data JSON object.

**Pagination header example**

    {
      // Whether the current page is the first of the collection
      "first_page": true,

      // Total amount of available pages
      "total_pages": 1,

      // Previous page number
      "previous_page": null,

      // Total number of items in the collection
      "total": 1,

      // Next page number
      "next_page": null,

      // Whether the current page is the last available one
      "last_page": true,

      // Record collection offset
      "offset": 0
    }

### Controlling pagination

Optional pagination parameters may be passed in the request URI in order to control the way the collection gets paginated. If parameters are incorrect a HTTP 400 Bad request status is returned along with an empty body.

| Parameter	| Default |	Acceptable values              |
|-----------|---------|--------------------------------|
| page      | 1       | Positive integer >0            |
| per_page  | 20      | Positive integer >=5 and <=100 |

## Localization

The relevant results and error messages will be localized to the language associated to the user, currently English and French are supported.

## Error handling

Whenever an error is encountered, the answer to a JSON API call will have :

 * An HTTP 422 status (Unprocessable entity) or HTTP 400 (Bad request),
 * A JSON array of localized error messages as body

## Successful calls

If the API call was successful, the platform will answer with :

 * An HTTP 200 status (OK) or HTTP 201 (Created),
 * A JSON representation of the entity being created or updated if relevant



# API Calls

## Account operations

An account operation is any ledger operation that changes the account's balance.

### Get the details of an account operation (A)
   
This call will return the details of a single account operation, the response contains : the UUID identifying the operation, the amount of this particular operation, its currency, its creation timestamp, its state (if relevant), a string indicating the type of the operation and the account balance that this operation led to (the sum of all transactions in the same currency including this one but not the ones that came after it).

**Request path :** `/api/v1/account_operations/{uuid}`

**Request method :** `GET`

**Request parameters**

| Name | Type | Description                   |
|------|------|-------------------------------|
| uuid | UUID | UUID of the account operation |

**Response**

A JSON object with the following attributes is returned :

| Name       | Type     | Description                                       |
|------------|----------|---------------------------------------------------|
| uuid       | UUID     | UUID of the account operation                     |
| amount     | Decimal  | Amount of the operation (1)                       |
| currency   | String   | Currency of the operation (2)                     |
| created_at | Datetime | Timestamp of operation creation                   |
| state      | String   | Operation state if relevant, `null` otherwise (3) |
| type       | String   | Operation type (4)                                |

 1. Credits are expressed as positive amounts, debits are expressed as negative amounts
 2. See currencies table
 3. See states table
 4. See operation types table
 
**Example request :** `GET /api/v1/account_operations/3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d`

**Example response :**
     
    {
      "uuid" : "3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d",
      "amount" : 50.0,
      "currency" : "EUR",
      "created_at" : "2013-01-14T16:28:57Z",
      "state" : null,
      "type" : "wire_deposit",
      "balance" : 550.0
    }
   
### Get a list of account operations (A,P)
   
This call will return a paginated list of account operations relative to the authenticated account.

**Request path :** `/api/v1/account_operations`

**Request method :** `GET`

**Request parameters**

_None_

**Response**

A JSON array of account operations is returned. The structure collection elements is detailed at "Get the details of an account operation".
 
**Example request :** `GET /api/v1/account_operations`

**Example response :**
    
	[ 
      {
        "uuid" : "3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d",
        "amount" : 50.0,
        "currency" : "EUR",
        "created_at" : "2013-01-14T16:28:57Z",
        "state" : null,
        "type" : "wire_deposit",
        "balance" : 550.0
      },
      {
        "uuid" : "b3c08962-4dc3-9ffc-4dc3-3a7bc1b2ff4d",
        "amount" : 500.0,
        "currency" : "EUR",
        "created_at" : "2013-01-10T12:45:50Z",
        "state" : null,
        "type" : "wire_deposit",
        "balance" : 500.0
      }      
    ]

## Send money

### Send Bitcoins (A)

This call will perform a Bitcoin transaction.

**Request path :** `/api/v1/transfers/send_bitcoins`

**Request method :** `POST`

**Request parameters**

| Name    | Type    | Description                 |
|---------|---------|-----------------------------|
| amount  | Decimal | Amount to send              |
| address | String  | Recipient's Bitcoin address |

**Response**

An `UUID` is returned after the request is queued for execution. It enables the client to make subsequent status check requests.
 
**Example request :** `POST /api/v1/transfers/send_bitcoins`

    {
      "amount" : 10.0,
      "address" : "1KfNzSKFAmpC4kNYaGLqj8LGPHxGmRG2nZ"
    }

**Example response :**
    
    {
      "uuid" : "3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d"     
    }

### Send money to an e-mail address (A)

This call will move money to the account identified with the given e-mail address. If no such account is found an e-mail gets sent inviting its recipient to create an account, or sign-in to one to retrieve the sent funds. If the amount isn't claimed after a week the funds are returned to the sender.

**Request path :** `/api/v1/transfers/send_to_email`

**Request method :** `POST`

**Request parameters**

| Name     | Type    | Description                               |
|----------|---------|-------------------------------------------|
| amount   | Decimal | Amount to send                            |
| currency | String  | Currency in which the amount is expressed |
| address  | String  | Recipient's e-mail address                |

**Response**

An `UUID` is returned after the request is queued for execution. It enables the client to make subsequent status check requests.
 
**Example request :** `POST /api/v1/transfers/send_bitcoins`

    {
      "amount" : 10.0,
      "currency" : "EUR",
      "address" : "david@bitcoin-central.net"
    }

**Example response :**
    
    {
      "uuid" : "3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d"     
    }

## Quotes

Quotes are a mechanism for clients to send funds or exchange them to other currencies. They provide clients with a guaranteed fixed-rate at which the system will convert their funds before crediting them to their account or send them out.

The canonical use of a quote is to pay in currency to a Bitcoin invoice, materialized as a QR code :

 1. User scans Bitcoin QR code
 2. Client app extracts requested Bitcoin amount
 3. Client app requests a quote for this Bitcoin amount to the API and provides the currency in which debit upon quote payment
 4. A guaranteed rate is returned by the API
 5. Client app shows price expressed in the user's currency
 6. After user confirmation the client app instructs the API to pay the quote to a specific Bitcoin address
 7. The merchant receives the payment in Bitcoin and the user is debited in his native currency
 
### Create a quote (A)

This call will create a quote. When doing so clients must specify a currency (the other currency is always assumed to be "BTC") an amount they are requesting and a direction. Combining these parameters in various ways will have the system address a wide array of use cases. 

For example a client can request :

 1. How much BTC would need to be sold to get exactly 10 EUR
 2. How much BTC could be bought with 10 EUR
 3. How much EUR would the sale of 1 BTC get
 4. How much EUR would be required to buy 1 BTC

To obtain the relevant quote a client would pass the following parameters :

| Case | Currency | direction | requested\_btc\_amount | requested\_currency\_amount |
|------|----------|-----------|------------------------|-----------------------------|
| 1    | EUR      | sell      | N/A                    | 10                          |
| 2    | EUR      | buy       | N/A                    | 10                          |
| 3    | EUR      | sell      | 1                      | N/A                         |
| 4    | EUR      | buy       | 1                      | N/A                         |

**Request path :** `/api/v1/quotes`

**Request method :** `POST`

**Request parameters**

| Name                        | Type    | Description                                                            |
|-----------------------------|---------|------------------------------------------------------------------------|
| requested\_currency\_amount | Decimal | Constrain on the currency amount (1)                                   |
| requested\_btc\_amount      | Decimal | Constrain on the Bitcoin amount  (1)                                   |
| direction                   | String  | Whether the quote should apply to a sale or a purchase of Bitcoins (2) |
| currency                    | String  | Currency in which the requested_amount is expressed                    |

 1. Exactly one of the currencies must be constrained, the other parameter may be omitted
 2. Acceptable values are `buy` and `sell`

**Response**

A JSON object with the following parameters is returned. If the current market depth or volatility does not allow for a quote to be given an error will be returned.

| Name                        | Type     | Description                                           |
|-----------------------------|----------|-------------------------------------------------------|
| uuid                        | UUID     | Quote identifier                                      |
| requested\_currency\_amount | Decimal  | The instructed currency amount or `null`              |
| requested\_btc\_amount      | Decimal  | The instructed Bitcoin amount or `null`               |
| direction                   | String   | The direction you provided                            |
| currency_amount             | Decimal  | The quoted currency amount or `null`                  | 
| btc_amount                  | Decimal  | The quoted Bitcoin amount or `null`                   | 
| rate                        | Decimal  | The quoted exchange rate                              |
| valid_until                 | Datetime | The timestamp at which this quote will be invalidated |
| created_at                  | Datetime | The creation date timestamp                           |
| executed                    | Boolean  | Whether this quote has already been settled or paid   |   


**Example request :** `POST /api/v1/quotes`

This demonstrates how to obtain a quote as described in the example use case #1.

    {
      "requested_amount" : 10.0,
      "currency" : "EUR",
      "direction" : "sell"
    }

**Example response :**
    
    {
      "uuid" : "3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d",
      "currency" : "EUR",
      "direction" : "sell",
      "rate" : 10.65
      "currency_amount" : null,
      "btc_amount" : 0.93896714
      "requested_currency_amount" : 10,
      "requested_btc_amount" : null,
      "valid_until" : "2013-01-10T13:00:50Z",
      "created_at" : "2013-01-10T12:45:50Z",
      "executed" : false
    }

### View a quote (A)

This call will return a JSON object representing a quote

**Request path :** `/api/v1/quotes/{uuid}`

**Request method :** `GET`

**Request parameters**

| Name | Type | Description      |
|------|------|------------------|
| uuid | UUID | Quote identifier |


**Response**

A JSON object with the following parameters is returned.

| Name                        | Type     | Description                                           |
|-----------------------------|----------|-------------------------------------------------------|
| uuid                        | UUID     | Quote identifier                                      |
| requested\_currency\_amount | Decimal  | The instructed currency amount or `null`              |
| requested\_btc\_amount      | Decimal  | The instructed Bitcoin amount or `null`               |
| direction                   | String   | The direction you provided                            |
| currency_amount             | Decimal  | The quoted currency amount or `null`                  | 
| btc_amount                  | Decimal  | The quoted Bitcoin amount or `null`                   | 
| rate                        | Decimal  | The quoted exchange rate                              |
| valid_until                 | Datetime | The timestamp at which this quote will be invalidated |
| created_at                  | Datetime | The creation date timestamp                           |
| executed                    | Boolean  | Whether this quote has already been settled or paid   |   


**Example request :** `GET /api/v1/quotes/3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d`

**Example response :**
    
    {
      "uuid" : "3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d",
      "currency" : "EUR",
      "direction" : "sell",
      "rate" : 10.65
      "currency_amount" : null,
      "btc_amount" : 0.93896714
      "requested_currency_amount" : 10,
      "requested_btc_amount" : null,
      "valid_until" : "2013-01-10T13:00:50Z",
      "created_at" : "2013-01-10T12:45:50Z",
      "executed" : false
    }

### List quotes (A,P)

This call will return a paginated list of quotes for the client account.

**Request path :** `/api/v1/quotes`

**Request method :** `GET`

**Request parameters**

N/A

**Response**

A JSON array of quote objects is returned.

**Example request :** `GET /api/v1/quotes`

**Example response :**
    
	[
   	  {
        "uuid" : "3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d",
        "currency" : "EUR",
        "direction" : "sell",
        "rate" : 10.65
        "currency_amount" : null,
        "btc_amount" : 0.93896714
        "requested_currency_amount" : 10,
        "requested_btc_amount" : null,
        "valid_until" : "2013-01-10T13:00:50Z",
        "created_at" : "2013-01-10T12:45:50Z",
        "executed" : true
      },
      {
        "uuid" : "4dc33a7bc1b2-9b7e-3a7b-9ffc-b3c08962ff4d",
        "currency" : "EUR",
        "direction" : "sell",
        "rate" : 10.65
        "currency_amount" : null,
        "btc_amount" : 0.93896714
        "requested_currency_amount" : 10,
        "requested_btc_amount" : null,
        "valid_until" : "2013-01-10T13:00:50Z",
        "created_at" : "2013-01-10T12:45:50Z",
        "executed" : true
      }
    ]

### Pay a quote (A)

This action applies to quotes for buying BTC. It will perform the exchange creating a user account debit of the calculated `currency_amount` or instructed `requested_currency_amount` and send out the calculated `btc_amount` or instructed `requested_btc_amount` to the Bitcoin address in the `address` field.

**Request path :** `/api/v1/quotes/{uuid}/pay`

**Request method :** `POST`

**Request parameters**

| Name    | Type   | Description           |
|---------|--------|-----------------------|
| uuid    | UUID   | Quote identifier      |
| address | String | Valid Bitcoin address |


**Response**

A quote JSON object is returned.

**Example request :** `GET /api/v1/quotes/3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d/pay`

**Example response :**
    
    {
      "uuid" : "3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d",
      "currency" : "EUR",
      "direction" : "sell",
      "rate" : 10.65
      "currency_amount" : null,
      "btc_amount" : 0.93896714
      "requested_currency_amount" : 10,
      "requested_btc_amount" : null,
      "valid_until" : "2013-01-10T13:00:50Z",
      "created_at" : "2013-01-10T12:45:50Z",
      "executed" : true
    }

### Execute a quote (A)

This action applies to quotes for buying BTC. It will perform the exchange creating user account debit and credit operations depending on the quote requested.

**Request path :** `/api/v1/quotes/{uuid}/execute`

**Request method :** `POST`

**Request parameters**

| Name    | Type   | Description           |
|---------|--------|-----------------------|
| uuid    | UUID   | Quote identifier      |


**Response**

A quote JSON object is returned.

**Example request :** `POST /api/v1/quotes/3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d/execute`

**Example response :**
    
    {
      "uuid" : "3a7bc1b2-9b7e-4dc3-9ffc-b3c08962ff4d",
      "currency" : "EUR",
      "direction" : "sell",
      "rate" : 10.65
      "currency_amount" : null,
      "btc_amount" : 0.93896714
      "requested_currency_amount" : 10,
      "requested_btc_amount" : null,
      "valid_until" : "2013-01-10T13:00:50Z",
      "created_at" : "2013-01-10T12:45:50Z",
      "executed" : true
    }

## Invoices

Invoices are requests for payment. They can be expressed in any arbitrary currency. They all get a unique Bitcoin payment address assigned and a Bitcoin amount calculated from the requested currency amount.

Payment can be made by sending the `btc_amount` amount of Bitcoins to the `payment_address` address or directly in the requested currency from another account. The invoice payment will trigger a `POST`to the `callback_url`.

### View an invoice (A)

This call will return a JSON object representing an invoice

**Request path :** `/api/v1/invoices/{uuid}`

**Request method :** `GET`

**Request parameters**

| Name | Type | Description      |
|------|------|------------------|
| uuid | UUID | Quote identifier |


**Response**

A JSON object with the following parameters is returned.

| Name                  | Type     | Description                                                     |
|-----------------------|----------|-----------------------------------------------------------------|
| uuid                  | UUID     | Invoice identifier                                              |
| state                 | String   | Invoice state _(see appendix)_                                  | 
| payment\_address      | String   | Bitcoin payment address                                         | 
| payment\_bitcoin_\uri | String   | Payment URI, should be used to generate QR codes                |
| amount                | Decimal  | Requested amount to be credited upon payment                    | 
| btc_amount            | Decimal  | Payable amount expressed in BTC                                 |
| currency              | String   | Currency in which the amount is expressed                       |
| merchant\_reference   | String   | Merchant reference                                              |
| merchant\_memo        | String   | Merchant memo                                                   |
| callback\_url         | String   | URL to which a callback should be made when the invoice is paid |
| item\_url             | String   | Order-related URL                                               |
| paid\_at              | Datetime | Payment timestamp                                               |
| created\_at           | Datetime | Creation timestamp                                              |
| updated\_at           | Datetime | Update timestamp                                                |
| expires\_at           | Datetime | Expiration timestamp                                            |
| settled               | Boolean  | Has this invoice already been credited ?                        |
| callback\_fired       | Boolean  | Has the HTTP callback been successfully fired ?                 |


**Example request :** `GET /api/v1/invoices/70c7936b-f8ce-443a-8338-3762de0a1e92`

**Example response :**
    
    {
      "uuid": "70c7936b-f8ce-443a-8338-3762de0a1e92",    
      "amount": 10.0, 
      "btc_amount": 1.021732, 
      "callback_fired": false, 
      "callback_url": null, 
      "created_at": "2013-01-21T10:20:07Z", 
      "currency": "EUR", 
      "expires_at": "2013-01-21T10:40:07Z", 
      "item_url": null, 
      "merchant_memo": null, 
      "merchant_reference": null, 
      "paid_at": null, 
      "payment_address": "1JnjJNhdKSgvMKr6xMbqVEudB3eACsGJSz", 
      "payment_bitcoin_uri" : "bitcoin:1JnjJNhdKSgvMKr6xMbqVEudB3eACsGJSz?amount=100.0&label=&x-pay-curamt=100.0&x-pay-cur=BTC&x-pay-id=7653453d-6372-4ffa-bc56-1e3182ef7f35",       
      "settled": false, 
      "state": "pending", 
      "updated_at": "2013-01-21T10:20:07Z"
    }

### View an invoice (Public)

It is the same call as the above one, except this call will return a subset of the JSON object representing an invoice. 

**Request path :** `/api/v1/invoices/{uuid}`

**Request method :** `GET`

**Request parameters**

| Name | Type | Description      |
|------|------|------------------|
| uuid | UUID | Quote identifier |


**Response**

A JSON object with the following parameters is returned.

| Name                | Type     | Description                                                     |
|---------------------|----------|-----------------------------------------------------------------|
| uuid                | UUID     | Invoice identifier                                              |
| state               | String   | Invoice state _(see appendix)_                                  | 
| payment\_address    | String   | Bitcoin payment address                                         |                    
| amount              | Decimal  | Requested amount to be credited upon payment                    | 
| btc_amount          | Decimal  | Payable amount expressed in BTC                                 |
| currency            | String   | Currency in which the amount is expressed                       |
| merchant\_reference | String   | Merchant reference                                              |
| merchant\_memo      | String   | Merchant memo                                                   |
| item\_url           | String   | Order-related URL                                               |
| paid\_at            | Datetime | Payment timestamp                                               |
| created\_at         | Datetime | Creation timestamp                                              |
| updated\_at         | Datetime | Update timestamp                                                |
| expires\_at         | Datetime | Expiration timestamp                                            |


**Example request :** `GET /api/v1/invoices/70c7936b-f8ce-443a-8338-3762de0a1e92`

**Example response :**
    
    {
      "uuid": "70c7936b-f8ce-443a-8338-3762de0a1e92",    
      "amount": 10.0, 
      "btc_amount": 1.021732, 
      "callback_fired": false, 
      "callback_url": null, 
      "created_at": "2013-01-21T10:20:07Z", 
      "currency": "EUR", 
      "expires_at": "2013-01-21T10:40:07Z", 
      "item_url": null, 
      "merchant_memo": null, 
      "merchant_reference": null, 
      "paid_at": null, 
      "payment_address": "1JnjJNhdKSgvMKr6xMbqVEudB3eACsGJSz",
      "payment_bitcoin_uri" : "bitcoin:1JnjJNhdKSgvMKr6xMbqVEudB3eACsGJSz?amount=100.0&label=&x-pay-curamt=100.0&x-pay-cur=BTC&x-pay-id=7653453d-6372-4ffa-bc56-1e3182ef7f35", 
      "settled": false, 
      "state": "pending", 
      "updated_at": "2013-01-21T10:20:07Z"
    }

### List invoices (A,P)

This call will return a paginated list of invoices for the client account.

**Request path :** `/api/v1/invoices`

**Request method :** `GET`

**Request parameters**

N/A

**Response**

A JSON array of invoice objects is returned.

**Example request :** `GET /api/v1/quotes`

**Example response :**
    
	[
      {
        "uuid": "8338936b-f8ce-443a-70c7-3762de0a1e92",    
        "amount": 10.0, 
        "btc_amount": 1.021732, 
        "callback_fired": false, 
        "callback_url": null, 
        "created_at": "2013-01-21T10:20:07Z", 
        "currency": "EUR", 
        "expires_at": "2013-01-21T10:40:07Z", 
        "item_url": null, 
        "merchant_memo": null, 
        "merchant_reference": null, 
        "paid_at": null, 
        "payment_address": "1JnjJNhdKSgvMKr6xMbqVEudB3eACsGJSz", 
        "settled": false, 
        "state": "pending", 
        "updated_at": "2013-01-21T10:20:07Z"
      },
      {
        "uuid": "70c7936b-f8ce-443a-8338-3762de0a1e92",    
        "amount": 10.0, 
        "btc_amount": 1.021732, 
        "callback_fired": false, 
        "callback_url": null, 
        "created_at": "2013-01-21T10:20:07Z", 
        "currency": "EUR", 
        "expires_at": "2013-01-21T10:40:07Z", 
        "item_url": null, 
        "merchant_memo": null, 
        "merchant_reference": null, 
        "paid_at": null, 
        "payment_address": "1JnjJNhdKSgvMKr6xMbqVEudB3eACsGJSz", 
        "settled": false, 
        "state": "pending", 
        "updated_at": "2013-01-21T10:20:07Z"
      }
    ]

### Create an invoice (A)

This call creates an invoice

**Request path :** `/api/v1/invoices`

**Request method :** `POST`

**Request parameters**

| Name                | Type    | Description                                                                  |
|---------------------|---------|------------------------------------------------------------------------------|
| amount              | Decimal | Requested amount to be credited upon payment                                 |
| currency            | String  | Currency in which the amount is expressed                                    |
| merchant\_reference | String  | Merchant reference _(optional)_                                              |
| merchant\_memo      | String  | Merchant memo _(optional)_                                                   |
| callback\_url       | String  | URL to which a callback should be made when the invoice is paid _(optional)_ |
| item\_url           | String  | Order-related URL _(optional)_                                               |

**Response**

An invoice JSON object is returned.

**Example request :** `POST /api/v1/invoices`

    {
      "amount" : 10.0, 
      "currency" : "EUR"
    }

**Example response :**
    
    {
      "uuid": "70c7936b-f8ce-443a-8338-3762de0a1e92",    
      "amount": 10.0, 
      "btc_amount": 1.021732, 
      "callback_fired": false, 
      "callback_url": null, 
      "created_at": "2013-01-21T10:20:07Z", 
      "currency": "EUR", 
      "expires_at": "2013-01-21T10:40:07Z", 
      "item_url": null, 
      "merchant_memo": null, 
      "merchant_reference": null, 
      "paid_at": null, 
      "payment_address": "1JnjJNhdKSgvMKr6xMbqVEudB3eACsGJSz", 
      "settled": false, 
      "state": "pending", 
      "updated_at": "2013-01-21T10:20:07Z"
    }

## Trading

### Place an order (A)

This call will place a trade order and queue it for execution.

**Request path :** `/api/v1/trade_orders`

**Request method :** `POST`

**Request parameters**

| Name     | Type    | Description                                      |
|----------|---------|--------------------------------------------------|
| amount   | Decimal | Amount of Bitcoins to trade                      |
| currency | String  | Currency to trade against                        |
| price    | Decimal | Price _(may be omitted to place a market order)_ |
| type     | String  | Must be `buy` (bid) or `sell` (ask)              |

**Response**

An trade order JSON object is returned with the following parameters

| Name              | Type     | Description                       |
|-------------------|----------|-----------------------------------|
| uuid              | UUID     | Trade order identifier            |
| amount            | Decimal  | Remaining Bitcoin amount to trade |
| instructed_amount | Decimal  | Amount of Bitcoins to trade       |
| currency          | String   | Currency to trade against         |
| price             | Decimal  | Price _(null for a market order)_ |
| type              | String   | Either `buy` or `sell`            |
| created_at        | Datetime | Creation timestamp                |
| updated_at        | Datetime | Update timestamp                  |

**Example request :** `POST /api/v1/trade_orders`

    {
      "amount" : 10.0, 
      "type" : "buy",
      "currency" : "EUR"
    }

**Example response :**
    
    {
      "amount": 10.0, 
      "type": "buy", 
      "created_at": "2013-01-21T22:07:15Z", 
      "instructed_amount": 10.0, 
      "price": null, 
      "state": "pending_execution", 
      "updated_at": "2013-01-21T22:07:15Z", 
      "uuid": "8b3c3446-9c5d-48a8-8044-08622cd4607b"
    }

### Cancel an order (A)

This call will enqueue a cancel order job to remove it from the order book.

**Request path :** `/api/v1/trade_orders/{uuid}`

**Request method :** `DELETE`

**Request parameters**

| Name | Type | Description            |
|------|------|------------------------|
| uuid | UUID | Trade order identifier |

**Example request :** `DELETE /api/v1/trade_orders/8b3c3446-9c5d-48a8-8044-08622cd4607b`

**Example response :**

N/A

### View trades for an order (A)

This call will return a collection of trades that were executed for a particular order.

**Request path :** `/api/v1/trade_orders/{uuid}/trades`

**Request method :** `GET`

**Request parameters**

| Name | Type | Description            |
|------|------|------------------------|
| uuid | UUID | Trade order identifier |

**Response**

An array of trade JSON objects is returned.

| Name            | Type     | Description                                      |
|-----------------|----------|--------------------------------------------------|
| uuid            | UUID     | Trade identifier                                 |
| traded_currency | Decimal  | Amount traded, expressed in `currency`           |
| traded_btc      | Decimal  | Amount of Bitcoins traded                        |
| currency        | String   | Currency in which `traded_currency` is expressed |
| price           | Decimal  | Price at which the exchange was made             |
| created_at      | Datetime | Creation timestamp                               |



**Example request :** `GET /api/v1/trade_orders/8b32ddf1-1675-4ed3-b1bb-b4efc4ecd98c/trades`

**Example response :**

    [
      {
        "created_at": "2013-01-22T08:19:41Z", 
        "currency": "EUR", 
        "price": 5.0, 
        "traded_btc": 980.0, 
        "traded_currency": 4940.0, 
        "uuid": "1c86abf0-170a-4101-84d1-cdad913c95dd"
      },
      {
        "created_at": "2013-01-22T08:19:41Z", 
        "currency": "EUR", 
        "price": 6.0, 
        "traded_btc": 10.0, 
        "traded_currency": 60.0, 
        "uuid": "170aabf0-1c86-4101-84d1-cdad913c95dd"
      }      
    ]

### View an order (A)

This call will return a trade order JSON object. 

**Request path :** `/api/v1/trade_orders/{uuid}`

**Request method :** `GET`

**Request parameters**

| Name | Type | Description      |
|------|------|------------------|
| uuid | UUID | Order identifier |

**Response**

A JSON object with the following parameters is returned.

| Name              | Type     | Description                       |
|-------------------|----------|-----------------------------------|
| uuid              | UUID     | Trade order identifier            |
| amount            | Decimal  | Remaining Bitcoin amount to trade |
| instructed_amount | Decimal  | Amount of Bitcoins to trade       |
| currency          | String   | Currency to trade against         |
| price             | Decimal  | Price _(null for a market order)_ |
| type              | String   | Either `buy` or `sell`            |
| created_at        | Datetime | Creation timestamp                |
| updated_at        | Datetime | Update timestamp                  |

**Example request :** `GET /api/v1/trade_orders/8b32ddf1-1675-4ed3-b1bb-b4efc4ecd98c`

**Example response :**
    
    {
      "amount": 10.0, 
      "type": "buy", 
      "created_at": "2013-01-22T08:19:37Z", 
      "instructed_amount": 1000.0, 
      "price": 11.0, 
      "state": "active", 
      "updated_at": "2013-01-22T08:19:41Z", 
      "uuid": "8b32ddf1-1675-4ed3-b1bb-b4efc4ecd98c"
    }

### List orders (A,P)

This call will return a paginated list of the client's trade orders.

**Request path :** `/api/v1/trade_orders`

**Request method :** `GET`

**Request parameters**

N/A

**Example request :** `GET /api/v1/trade_orders`

**Example response :**

    [  
      {
        "amount": 10.0, 
        "type": "buy", 
        "created_at": "2013-01-21T22:15:38Z", 
        "instructed_amount": 10.0, 
        "price": null, 
        "state": "pending_execution", 
        "updated_at": "2013-01-21T22:15:38Z", 
        "uuid": "52823408-972f-4551-acc5-e7d3f032c6d5"
      }, 
      {
        "amount": 10.0, 
        "type": "buy", 
        "created_at": "2013-01-21T22:15:40Z", 
        "instructed_amount": 10.0, 
        "price": null, 
        "state": "pending_execution", 
        "updated_at": "2013-01-21T22:15:40Z", 
        "uuid": "6e3ea778-9ef7-4e4f-9910-85e735f7b42a"
      }
    ]


## Coupons

Coupons are a way to easily move money between accounts, they are debited from the issuer's account upon creation and may be redeemed at anytime against any account (including the issuer).

They are materialized by a unique redemption code. This code should be kept private as anyone having knowledge of it can redeem the funds.

### Create a coupon (A)

This call issues a coupon

**Request path :** `/api/v1/coupons`

**Request method :** `POST`

**Request parameters**

| Name                | Type    | Description                               |
|---------------------|---------|-------------------------------------------|
| amount              | Decimal | Currency value for the issued coupon      |
| currency            | String  | Currency in which the amount is expressed |

**Response**

A coupon code is returned

**Example request :** `POST /api/v1/coupons`

    {
      "amount" : 12.0, 
      "currency" : "EUR"
    }

**Example response :**
    
    {
      "code": "BP-EUR-9660407B43799CCED320"
    }

### View a coupon

This call will return a JSON object representing a coupon

**Request path :** `/api/v1/coupons/{code}`

**Request method :** `GET`

**Request parameters**

| Name | Type   | Description      |
|------|--------|------------------|
| code | String | Coupon code      |


**Response**

A JSON object with the following parameters is returned.

| Name                  | Type     | Description                               |
|-----------------------|----------|-------------------------------------------|
| uuid                  | UUID     | Coupon account operation identifier       |
| code                  | String   | Coupon code                               |
| state                 | String   | Coupon state _(see appendix)_             | 
| amount                | Decimal  | Coupon value                              | 
| currency              | String   | Currency in which the amount is expressed |
| created\_at           | Datetime | Creation timestamp                        |


**Example request :** `GET /api/v1/coupons/BP-EUR-9660407B43799CCED320`

**Example response :**
    
    {
      "amount": -12.0, 
      "code": "BP-EUR-9660407B43799CCED320", 
      "created_at": "2013-01-30T11:52:36Z", 
      "currency": "EUR", 
      "state": "pending", 
      "uuid": "c21adaf6-f5a2-4d93-a762-a63b89b52265"
    }

### Redeem a coupon (A)

This call will a redeem a coupon to the client's account. It returns an `UUID`, this identifier can be used to request details about the account operation created for the client's account by the redemption of the coupon (credit of the coupon value).

This call may be used to void a coupon by redeeming it against the account that issued it.

**Request path :** `/api/v1/coupons/redeem`

**Request method :** `POST`

**Request parameters**

| Name | Type   | Description      |
|------|--------|------------------|
| code | String | Coupon code      |

**Response**

A JSON object with the following parameters is returned.

| Name                  | Type     | Description                               |
|-----------------------|----------|-------------------------------------------|
| uuid                  | UUID     | Redemption account operation identifier   |

**Example request :** `POST /api/v1/coupons`

    {
      "code": "BP-EUR-9660407B43799CCED320"
    }

**Example response :**
    
    {
      "uuid": "3e0004cd-158c-40d6-b8f9-f4b672e86308"
    }

# Appendix

## Codes and types tables

### Currencies

The following currencies are available :

| Symbol | Currency       |
|--------|----------------|
| BTC    | Bitcoin        |
| EUR    | Euro           |
| GBP    | Pound Sterling |
| USD    | US Dollar      |


### Operation types

| Type                        | Description                                             |
|-----------------------------|---------------------------------------------------------|
| fee                         | Trading fee                                             |
| coupon                      | Issue of a redeemable code                              |
| bitcoin_transfer            | Bitcoin withdrawal                                      |
| wire_transfer               | Wire transfer withdrawal                                |
| email_transfer              | Transfer of funds to an e-mail address                  |
| coupon_redemption           | Redemption of a redeemable code                         |
| email\_transfer\_redemption | Redemption of an e-mail transfer                        |
| charge                      | Generic charge                                          |
| bank_charge                 | Bank charge                                             |
| invoice_credit              | Payment of an invoice you issued (credits your account) |
| invoice_payment             | Payment for an invoice (debits your account)            |
| wire_deposit                | Deposit by bank wire                                    |
| trade                       | Trade                                                   |
| reversal                    | Reversal                                                |

### States

#### Transfers (Bitcoin transfer, Wire transfer)

The state of a transfer lets you check whether it has been sent out to the underlying network (banking network or Bitcoin network).

| State     | Description                           |
|-----------|---------------------------------------|
| pending   | The transfer hasn't been sent out yet |
| processed | The transfer has been sent out        |

#### Coupons

| State    | Description                             |
|----------|-----------------------------------------|
| pending  | The coupon is valid and may be redeemed |
| redeemed | The coupon has already been redeemed    |
| canceled | The coupon was redeemed to the issuer   |

#### E-mail transfers

| State                | Description                                                              |
|----------------------|--------------------------------------------------------------------------|
| pending_collection   | The recipient hasn't claimed the e-mail transfer amount yet              |
| canceled             | The e-mail transfer was canceled                                         |
| expired              | The transfer has expired                                                 |
| unreachable_receiver | An error occurred while sending the e-mail notification to its recipient |
| processed            | The recipient has collected the sent amount                              |

#### Invoices

| State          | Description                                     |
|----------------|-------------------------------------------------|
| pending        | The invoice is pending payment                  |
| partially_paid | The invoice has a partially confirmed payment   |       
| confirming     | A full or over-payment is confirming            |
| paid           | The invoice has a confirmed payment             |
| overpaid       | The invoice has a confirmed over-payment        |
| error          | The payment could not be converted as requested |

#### Trade orders

| State              | Description                                                                    |
|--------------------|--------------------------------------------------------------------------------|
| pending_execution  | The order has been queued for execution                                        |
| active             | The order has been executed but not filled it is visible in the order book (1) |
| filled             | The order has been filled                                                      |
| canceled           | The order has been canceled                                                    |
| insufficient_funds | The order cannot execute further due to lack of funds (2)                      |

 1. The amount shown in the order book is the amount that is actually executable given your balance, not necessarily the instructed amount
 2. Order execution resumes as soon as more funds are available

# Left TODO

## Websockets

 * Document the socket.io API
 
## Misc

 * Get an estimate (unsaved quote)
 * Add a `/me` API call
 * Add a call for account balances
