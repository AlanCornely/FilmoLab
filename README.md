# 🎬 FilmoLab

O **FilmoLab** é um aplicativo móvel desenvolvido em Kotlin e Jetpack Compose, projetado para ser um laboratório de filmes onde os usuários podem pesquisar, explorar detalhes, e gerenciar suas listas de favoritos e histórico de visualização.

## ✨ Funcionalidades Principais

*   **Pesquisa de Filmes:** Utilize a API OMDB para buscar filmes por título.
*   **Detalhes do Filme:** Visualize informações completas sobre qualquer filme, incluindo sinopse, elenco, e classificações.
*   **Lista de Favoritos:** Salve seus filmes preferidos localmente usando o banco de dados Room.
*   **Histórico de Pesquisa/Visualização:** Mantenha um registro dos filmes que você pesquisou ou visualizou.
*   **Interface Moderna:** Desenvolvido com a *UI toolkit* declarativa **Jetpack Compose**.

## 🛠️ Tecnologias Utilizadas

O projeto FilmoLab foi construído com as seguintes tecnologias e bibliotecas:

| Categoria | Tecnologia | Descrição |
| :--- | :--- | :--- |
| **Linguagem** | Kotlin | Linguagem de programação oficial para o desenvolvimento Android. |
| **UI** | Jetpack Compose | *Toolkit* moderna para construção de interfaces nativas Android. |
| **Arquitetura** | MVVM (implícito) | Separação de preocupações com `ViewModel` para gerenciamento de estado. |
| **Rede** | Retrofit & OkHttp | Cliente HTTP para comunicação eficiente com a API OMDB. |
| **Banco de Dados** | Room Persistence Library | Abstração de banco de dados SQLite para persistência local de Favoritos e Histórico. |
| **Assincronicidade** | Kotlin Coroutines | Gerenciamento de tarefas assíncronas e concorrentes. |
| **Imagens** | Coil | Biblioteca de carregamento de imagens moderna e rápida para Compose. |
| **Navegação** | Navigation Compose | Componente para navegação entre telas do aplicativo. |

## ⚙️ Configuração e Instalação

Para rodar o FilmoLab localmente, você precisará ter o **Android Studio** instalado.

### Pré-requisitos

*   Android Studio (versão recomendada: Giraffe ou superior).
*   SDK do Android 34.
*   Acesso à Internet para resolver dependências e buscar dados da API.

### Chave da API OMDB

O aplicativo utiliza a **OMDB API** para buscar dados de filmes. A chave da API deve ser configurada no código.

**Observação:** No código-fonte analisado, a chave da API está *hardcoded* no arquivo `OmdbApi.kt` para fins de demonstração:

```kotlin
// FilmoLab/app/src/main/kotlin/com/filmolab/network/OmdbApi.kt
companion object {
    const val API_KEY = "13fe47be" // Chave de demonstração
}
```

Para uso em produção ou para evitar limites de taxa, é **altamente recomendado** que você:

1.  Obtenha sua própria chave no site oficial da [OMDB API](http://www.omdbapi.com/).
2.  Substitua a chave de demonstração no arquivo `OmdbApi.kt`.
3.  Considere armazenar a chave em um arquivo de configuração seguro (como `local.properties`) e acessá-la via `BuildConfig`.

### Passos para Execução

1.  **Clone o repositório** (ou descompacte o arquivo zip).
2.  Abra o projeto no **Android Studio**.
3.  O Android Studio irá sincronizar automaticamente as dependências do Gradle.
4.  Selecione um emulador ou um dispositivo físico conectado.
5.  Clique no botão **Run** (▶️) para construir e instalar o aplicativo.

## 📂 Estrutura do Projeto

A estrutura do projeto segue as melhores práticas do desenvolvimento Android com Kotlin e Compose:

```
FilmoLab/
├── app/
│   ├── src/main/kotlin/com/filmolab/
│   │   ├── model/             # Classes de dados (MovieDetail, MovieSearchResponse) e Room (AppDatabase, MovieDao)
│   │   ├── network/           # Configuração de rede (RetrofitClient, OmdbApi)
│   │   └── ui/                # Componentes de UI (Screens, ViewModels, Theme)
│   └── build.gradle.kts       # Configuração e dependências do módulo
└── README.md                  # Este arquivo
```
