# CTOManager

Sistema de gerenciamento de CTOs com monitoramento de sensores e integração com SNS da AWS.

## 📋 Pré-requisitos

Antes de começar, você precisa ter instalado em sua máquina:

- **Java JDK 21** (ou superior)
- **Maven** (ou use o wrapper `./mvnw` incluído)
- **Docker Desktop** (para rodar o Localstack)
- **Git** (para clonar o repositório)

## 🚀 Configuração do Ambiente

### 1. Clone o repositório
```bash
git clone [URL DO REPOSITORIO]
cd CTOManager
```

### 2. Suba o Localstack (SNS local)
O projeto usa SNS da AWS para receber notificações. Para simular isso localmente:

```bash
docker-compose up -d
```

Isso irá iniciar o Localstack na porta `4566` com o serviço SNS habilitado.

> **Nota:** Aguarde alguns segundos para o container iniciar completamente.

### 3. Configure o SNS no Localstack
Após o container estar rodando, crie um tópico e uma assinatura:

```bash
# Criar tópico
aws --endpoint-url=http://localhost:4566 sns create-topic --name cto-alarmes

# Criar assinatura (URL pública ou use ngrok se estiver testando localmente)
aws --endpoint-url=http://localhost:4566 sns subscribe \
    --topic-arn arn:aws:sns:us-east-1:000000000000:cto-alarmes \
    --protocol http \
    --notification-endpoint http://host.docker.internal:8080/api/ctos/sns
```

> **Alternativa:** Se estiver no Linux, use `http://localhost:8080/api/ctos/sns` em vez de `host.docker.internal`.

### 4. Execute a aplicação

#### Opção A: Usando Maven Wrapper (recomendado)
```bash
# No Windows:
./mvnw spring-boot:run

# No Linux/Mac:
./mvnw spring-boot:run
```

#### Opção B: Usando Maven instalado
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 5. Acesse o banco de dados H2
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:argusdb`
- **Username:** `sa`
- **Password:** *(deixe em branco)*

## 📦 Estrutura do Projeto

```
CTOManager/
├── src/
│   ├── main/
│   │   ├── java/com/breno/CTOManager/
│   │   │   ├── Controller/     # Endpoints REST
│   │   │   ├── Entity/          # Entidades JPA
│   │   │   ├── Repository/      # Camada de dados
│   │   │   └── Service/         # Regras de negócio
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # Testes unitários
├── docker-compose.yml           # Configuração do Localstack
├── pom.xml                      # Dependências Maven
└── mvnw                         # Maven Wrapper
```

## 🔌 Endpoints da API

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/api/ctos` | Lista todas as CTOs |
| POST | `/api/ctos` | Cria uma nova CTO |
| DELETE | `/api/ctos/{id}` | Remove uma CTO |
| POST | `/api/ctos/sns` | Webhook para notificações SNS |
| GET | `/api/modelos` | Lista modelos de CTO |
| GET | `/api/sensores` | Lista sensores |

## 📝 Exemplo de Payload

### Criar CTO
```json
{
  "nome": "CTO-Centro-01",
  "latitude": -23.5505,
  "longitude": -46.6333,
  "modelo": {
    "id": 1
  }
}
```

## 🐳 Comandos úteis do Docker

```bash
# Parar o Localstack
docker-compose down

# Ver logs do Localstack
docker-compose logs -f

# Reiniciar o Localstack
docker-compose restart
```

## ⚠️ Solução de problemas comuns

### Erro: "Já existe uma CTO com esse nome"
- O nome da CTO deve ser único no sistema.

### Erro ao excluir CTO com status ALARMADO
- Não é permitido excluir CTOs que estão com sensor alarmado. Resolva o alarme primeiro.

### Localstack não conecta
- Verifique se o Docker está rodando: `docker ps`
- Confirme se a porta 4566 não está ocupada: `netstat -ano | findstr :4566`

### Dependências do Maven não baixam
- Execute `./mvnw clean install` para forçar o download das dependências.
