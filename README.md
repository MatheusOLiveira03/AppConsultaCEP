# 📍 AppConsultaCEP

Um aplicativo Android desenvolvido em **Kotlin** que permite consultar informações de endereço através de um CEP (Código de Endereçamento Postal) utilizando a API ViaCEP.

## 🎯 Objetivo

O AppConsultaCEP é uma aplicação mobile que oferece uma forma rápida e intuitiva para usuários brasileiros buscarem informações de endereço completas fornecendo apenas um CEP de 8 dígitos. A aplicação integra-se com a API pública ViaCEP para obter dados precisos e atualizados.

## ✨ Funcionalidades

- ✅ **Consulta de CEP**: Digite um CEP de 8 dígitos e obtenha informações completas do endereço
- ✅ **Validação em Tempo Real**: A aplicação valida se o CEP possui exatamente 8 dígitos
- ✅ **Interface Intuitiva**: Design moderno e responsivo com cards e elementos visuais organizados
- ✅ **Exibição de Dados**: Mostra logradouro, bairro, cidade, UF e DDD
- ✅ **Integração com API**: Consulta externa à API ViaCEP para obter dados precisos
- ✅ **Requisições Assíncronas**: Utiliza corrotinas Kotlin para não bloquear a UI

## 📱 Requisitos do Sistema

- **Android SDK**: API Level 24+ (Android 7.0)
- **Target SDK**: API Level 36 (Android 15)
- **Java Version**: 11 ou superior

## 🏗️ Arquitetura e Estrutura do Projeto

```
AppConsultaCEP/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/appconsultacep/
│   │   │   │   ├── MainActivity.kt              # Activity principal
│   │   │   │   ├── api/
│   │   │   │   │   ├── ViaCepClient.kt          # Cliente Retrofit singleton
│   │   │   │   │   └── ViaCepService.kt         # Interface de requisições
│   │   │   │   └── model/
│   │   │   │       └── ResponseEndereco.kt      # Data class de resposta
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml        # Layout da interface
│   │   │   │   ├── values/
│   │   │   │   ├── drawable/
│   │   │   │   └── mipmap-*/                    # Ícones do app
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   └── androidTest/
│   ├── build.gradle.kts                          # Configuração do app
│   └── proguard-rules.pro
├── build.gradle.kts                              # Configuração raiz
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## 📦 Dependências Principais

### HTTP Client e API
- **Retrofit 2.11.0**: Framework para consumir APIs REST
- **Retrofit Gson Converter 2.11.0**: Conversor JSON para objetos Kotlin

### AndroidX
- **androidx-core-ktx**: Extensões Kotlin para core Android
- **androidx-appcompat**: Compatibilidade com versões anteriores
- **androidx-activity**: Activity APIs modernizadas
- **androidx-constraintlayout**: Layout responsivo

### UI
- **Material Design**: Material components para Android

### Testes
- **JUnit**: Testes unitários
- **Espresso**: Testes de instrumentação
- **AndroidX Test JUnit**: Framework de teste Android

## 🔌 Componentes Principais

### 1. **MainActivity.kt**
Atividade principal que gerencia a UI e a lógica de interação do usuário.

**Principais responsabilidades:**
- Inicializar componentes da interface (EditTexts, Button)
- Validar entrada de CEP (deve ter 8 dígitos)
- Disparar requisição à API quando botão é clicado
- Exibir resultados nos campos de texto

**Código-chave:**
```kotlin
btnConsultar.setOnClickListener {
    val cep = edtCep.text.toString()
    if (cep.length != 8) {
        edtCep.error = "CEP inválido"
        return@setOnClickListener
    }
    lifecycleScope.launch {
        val endereco = ViaCepClient.instance.buscarEndereco(cep)
        // Preencher campos com dados
    }
}
```

### 2. **ViaCepClient.kt**
Singleton que configura e fornece instância única do cliente Retrofit.

**Características:**
- Base URL: `https://viacep.com.br`
- Usa Gson para conversão JSON
- Padrão Lazy Initialization

```kotlin
object ViaCepClient {
    private const val BASE_URL = "https://viacep.com.br"
    val instance: ViaCepService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViaCepService::class.java)
    }
}
```

### 3. **ViaCepService.kt**
Interface que define os endpoints da API ViaCEP.

```kotlin
interface ViaCepService {
    @GET("/ws/{cep}/json/")
    suspend fun buscarEndereco(@Path("cep") cep: String): ResponseEndereco
}
```

**Detalhe:** Usa `suspend` para suportar requisições assíncronas com corrotinas.

### 4. **ResponseEndereco.kt**
Data class que mapeia a resposta JSON da API.

```kotlin
data class ResponseEndereco(
    val logradouro: String,
    val bairro: String,
    val localidade: String,
    val uf: String,
    val ddd: String
)
```

### 5. **activity_main.xml**
Layout XML que define a interface do usuário com:
- Card de entrada com campo de CEP
- Botão de consulta
- Card de resultados com campos desabilitados mostrando:
  - Logradouro
  - Bairro
  - Cidade (Localidade)
  - UF (Estado)
  - DDD (Código de área)

**Design:**
- Fundo azul claro (#F4F7FB)
- Cards com bordas arredondadas (20dp)
- Botão azul (#03A9F4)
- ScrollView para suportar pequenas telas

## 🚀 Como Usar

### Pré-requisitos
- Android Studio instalado
- SDK Android 24+ configurado
- Conexão com internet (necessária para consultar API)

### Instalação e Execução

1. **Clone o repositório:**
```bash
git clone https://github.com/MatheusOLiveira03/AppConsultaCEP.git
cd AppConsultaCEP
```

2. **Abra no Android Studio:**
```bash
# Abra o projeto no Android Studio
```

3. **Sincronize as dependências:**
```bash
# Gradle sync automaticamente
```

4. **Compile e execute:**
- Selecione um emulador ou dispositivo físico
- Clique em "Run" ou pressione Shift+F10

### Uso da Aplicação

1. Abra o app
2. Digite um CEP de 8 dígitos no campo de entrada (ex: 12345678)
3. Clique no botão "Consultar CEP"
4. Os dados do endereço aparecerão nos campos abaixo:
   - Logradouro (rua/avenida)
   - Bairro
   - Cidade
   - UF (estado)
   - DDD (código de área)

## 🔐 Permissões

O aplicativo requer a seguinte permissão (definida em `AndroidManifest.xml`):

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

Esta permissão é necessária para fazer requisições HTTP à API ViaCEP.

## 🌐 API Utilizada

### ViaCEP
- **URL Base:** `https://viacep.com.br`
- **Endpoint:** `/ws/{CEP}/json/`
- **Método:** GET
- **Exemplo:** `https://viacep.com.br/ws/01310100/json/`

**Resposta:**
```json
{
    "logradouro": "Avenida Paulista",
    "bairro": "Bela Vista",
    "localidade": "São Paulo",
    "uf": "SP",
    "ddd": "11"
}
```

**Documentação:** https://viacep.com.br/

## 🛠️ Desenvolvimento

### Tecnologias Utilizadas
- **Linguagem:** Kotlin
- **Build Tool:** Gradle (Kotlin DSL)
- **HTTP Client:** Retrofit 2
- **Serialização:** Gson
- **Async:** Corrotinas Kotlin
- **UI Framework:** AndroidX

### Versões Importantes
- **compileSdk:** 36
- **minSdk:** 24
- **targetSdk:** 36
- **versionCode:** 1
- **versionName:** "1.0"

## 📋 Padrões de Código

O projeto segue padrões Android estabelecidos:

- **Activity-based:** Usa Activity tradicional (não Fragment)
- **View Binding:** Acesso direto a views via findViewById
- **Coroutines:** Operações assíncronas sem callbacks
- **Retrofit:** Padrão padrão para HTTP em Android
- **Singleton:** ViaCepClient usa padrão singleton com lazy initialization

## 🐛 Tratamento de Erros

Atualmente, o aplicativo inclui:
- ✅ Validação de comprimento do CEP (8 dígitos)
- ✅ Feedback visual com `EditText.error`

**Melhorias Futuras Recomendadas:**
- [ ] Tratamento de exceções de rede
- [ ] Feedback com Toast para erros
- [ ] Loading spinner durante requisição
- [ ] Retry automático em caso de falha
- [ ] Validação de CEP formato correto

## 📝 Fluxo de Funcionamento

```
Usuário digita CEP
        ↓
Clica em "Consultar CEP"
        ↓
Validação (8 dígitos?)
        ↓
Requisição HTTP à API ViaCEP
        ↓
Parse JSON → ResponseEndereco
        ↓
Preencher campos de resultado
        ↓
Exibir ao usuário
```

## 🔄 Fluxo de Corrotinas

```
lifecycleScope.launch {
    ↓
suspend fun buscarEndereco(cep: String)
    ↓
HTTP Request (não bloqueia UI)
    ↓
Retorna ResponseEndereco
    ↓
MainActivity atualiza TextFields
```

## 📊 Variáveis de Ambiente

Atualmente, a aplicação não requer variáveis de ambiente. A URL base da API é hardcoded no `ViaCepClient`.

## 🤝 Contribuindo

Para contribuir com o projeto:

1. Faça um Fork do repositório
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Melhorias Futuras

- [ ] Salvar consultas recentes em banco de dados local (Room)
- [ ] Histórico de buscas
- [ ] Compartilhamento de endereço
- [ ] Modos claro/escuro melhorados
- [ ] Testes unitários e instrumentados
- [ ] Tratamento robusto de erros
- [ ] Tela de splash
- [ ] Animações de transição

## 📄 Licença

Este projeto é de código aberto. Sinta-se livre para usar, modificar e distribuir.

## 👨‍💻 Autor

**Matheus O Oliveira**
- GitHub: [@MatheusOLiveira03](https://github.com/MatheusOLiveira03)
- Repositório: [AppConsultaCEP](https://github.com/MatheusOLiveira03/AppConsultaCEP)

## 📧 Contato

Para dúvidas ou sugestões, abra uma issue no repositório.

---

**Desenvolvido com ❤️ em Kotlin**
