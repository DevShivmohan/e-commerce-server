# e-commerce-server for Blue Synergy task

Implemented API description -
I have added 1 more api that are getting product list.

1. Getting product list :- GET ->  localhost:5050/ecommerce/products and return response
[
    {
        "id": 1,
        "name": "D set",
        "description": "D gtx hddjhd",
        "price": 8.0
    },
    {
        "id": 2,
        "name": "A set",
        "description": "A gtx hddjhd",
        "price": 58.0
    }
]

2. Create a customer :- POST -> localhost:5050/ecommerce/add-customer with request body 
{
    "email":"nshiv@gmail.com",
    "name":"Shiv"
}

 3. Add product into Shopping cart :- POST -> localhost:5050/ecommerce/add-cart with request body
{
    "email":"nshiv@gmail.com",
    "products":[
        {
            "productId":1,
            "qty":3
        },
        {
            "productId":3,
            "qty":2
        },
        {
            "productId":4,
            "qty":5
        }
    ]
}

4. Create Order with Shopping cart :- POST -> localhost:5050/ecommerce/create-order with request body, 
order created only when shopping cart has at least 1 product after order placed shopping cart element will remove
{
    "email":"nshiv@gmail.com",
    "name":"Shiv"
}

