# 🐾 PetNet - Desafio GetNet

Aplicativo mobile de um petshop virtual, desenvolvido como parte do desafio da GetNet. O app permite visualizar produtos para pets, ver detalhes, adicionar ao carrinho e finalizar a compra de forma simples e intuitiva.

## 📱 Telas do aplicativo
Figma - https://www.figma.com/design/SGy6x5FMcayrV58aBrqNOx/Desafio-GetNet?node-id=1-3&t=48g8yRLEo0KWsezq-0

## 🛠️ Tecnologias Utilizadas

- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- [Retrofit](https://square.github.io/retrofit/)
- [Koin](https://insert-koin.io/) (Injeção de dependência)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Coil](https://coil-kt.github.io/coil/) (Carregamento de imagens)

## 📦 Arquitetura

O projeto segue o padrão Clean Architecture** com **MVVM, separando claramente camadas de domínio, dados e apresentação. Isso facilita a escalabilidade, manutenibilidade e testabilidade do app.

## 💡 Decisões de Implementação

- A arquitetura **Clean Architecture + MVVM** foi adotada para garantir organização, desacoplamento e testes mais eficazes.

- A `CartViewModel` é compartilhada entre diferentes telas para manter o estado do carrinho centralizado durante a navegação.  
  ⚠️ *Observação*: Em um cenário real, o ideal seria persistir esse estado usando uma base local como o Room para maior robustez.

- O projeto já está **configurado para testes**, com um **teste instrumentado de exemplo** incluído. A intenção é ampliar a cobertura futuramente, adicionando testes unitários e de integração.

- A tela inicial (`Home`) exibe uma lista com informações resumidas dos produtos (nome, imagem e preço).  
  Ao clicar em um item, uma nova requisição é realizada para obter os detalhes completos, como descrição, dimensões, peso etc.

- Essa decisão otimiza o carregamento inicial e melhora a performance.  
  É interessante implementar paginaçãon futura para lidar melhor com listas grandes e garantir fluidez.
