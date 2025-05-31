# Avaliação Técnica: Java / L2

## Como executar a aplicação

```bash
docker build -t empacotamento .
docker run -p 8080:8080 empacotamento
```

## URLs da documentação via Swagger

1.  **Swagger UI**

```
http://localhost:8080/swagger.html
```

1.  **OpenAPI JSON**

```
http://localhost:8080/api-docs
```

## Endpoint

### `POST /pedido/processar-empacotamento`

-  **Descrição:** Processa o empacotamento a partir de uma lista de pedidos.

-  **Método HTTP:**  `POST`

-  **URL:**  `/pedido/processar-empacotamento`

-  **Corpo da Requisição:**

```json
{
    "pedidos": [
        {
            "pedido_id": 1,
            "produtos": [
                {
                    "produto_id": "Produto 1",
                    "dimensoes": {
                        "altura": 40,
                        "largura": 50,
                        "comprimento": 80
                    }
                },
                {
                    "produto_id": "Produto 2",
                    "dimensoes": {
                        "altura": 50,
                        "largura": 60,
                        "comprimento": 80
                    }
                }
            ]
        }
    ]
}
```

-  **Resposta de Sucesso (200 OK):**

```json
{
    "pedidos": [
        {
            "caixas": [
                {
                    "caixa_id": "Caixa 3",
                    "produtos": [
                        "Produto 2"
                    ]
                },
                {
                    "caixa_id": "Caixa 2",
                    "produtos": [
                        "Produto 1"
                    ]
                }
            ],
            "pedido_id": 1
        }
    ]
}
```
