# ROTA INTELIGENTE — projeto Android inicial

## O que já está incluído
- Projeto Android nativo em Kotlin.
- Calculadora de lucro líquido estimado.
- Campos editáveis para pagamento, km, tempo, combustível, consumo e manutenção.
- Serviço de bolha flutuante básico.
- Esqueleto de serviço de acessibilidade, sem aceitar/recusar pedidos.

## O que ainda não está implementado
- Captura de tela via MediaProjection e OCR ML Kit.
- Arrastar/recolher bolha e mostrar resultado em overlay.
- Regras de reconhecimento específicas para Shippify, 99 Entrega, Bee e iFood.
- Histórico persistente e relatórios.
- Testes em aparelho real, assinatura e APK de produção.

## Como abrir
1. Extraia o ZIP.
2. Abra a pasta `RotaInteligenteAndroid` no Android Studio.
3. Aguarde a sincronização do Gradle.
4. Execute em aparelho Android para testar a calculadora.
5. Conceda permissão de sobreposição apenas se quiser testar a bolha.

A leitura automática de telas requer consentimento explícito e implementação/testes adicionais. A disponibilidade e as políticas de cada aplicativo de entregas podem limitar a leitura. O app não deve automatizar aceite ou recusa.

## Gerar APK sem instalar Android Studio no celular
O ZIP inclui um workflow GitHub Actions em `.github/workflows/android-apk.yml`.
1. Crie um repositório no GitHub e envie todo o conteúdo desta pasta para a branch `main`.
2. Abra a aba Actions do repositório.
3. Execute `Build APK` (Run workflow) ou aguarde o build após o push.
4. Quando terminar, abra a execução e baixe o artifact `rota-inteligente-debug-apk`.
5. Extraia o artifact e instale `app-debug.apk` no Android, autorizando a instalação dessa fonte quando o sistema pedir.

Esse APK é de depuração para testes, não uma versão assinada para publicação na Play Store.
