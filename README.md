# Agenda oficial

Webscrapper que retira dados do site [oficial da agenda presidencial](https://www.gov.br/planalto/pt-br/acompanhe-o-planalto/agenda-do-presidente-da-republica) calculando em minutos o tempo informado entre os compromissos.

## Uso

Execução sem parâmetros apenas irá informar as horas trabalhadas do dia atual

## Parâmetros
### dataInicial=mm-aaaa

Informe o mês e ano da data inicial a ser extraído. Informado apenas a data inicial será processado todos os dias do mês até a data inicial. No final do processo um arquivo CSV será criado.

### dataFinal=mm-aaaa

Informe o mês e ano da data final a ser extraído. Se torna obrigatório informar a dataInicial caso este parâmetro seja utilizado. Um período entre a data inicial e final será extraído e um arquivo CSV será criado no final do processo.
