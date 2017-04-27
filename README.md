# EDII_TRABALHO1
Primeiro trabalho de Algoritmos e Estrutura de Dados

## Introdução e objetivo do trabalho
IMDBs são banco de dados que não trabalham com gestão de arquivos físicos, mantendo todos os dados e índices em memória. Dessa forma, são banco de dados mais simples, sendo seu uso restrito à capacidade de memória RAM do computador, mas o que fez as consultas serem processadas de forma bem rápida. O objetivo do trabalho é simular um IMDB. Embora os arquivos que trabalharemos são arquivos de dumps SQL, não é objetivo desse trabalho realizar parser de instruções SQL.

## Descrição do trabalho
Usaremos como referência o benchmark USDA disponível em http://pgfoundry.org/frs/?group_id=1000150. O USDA é um banco de dados de alimentos do departamento de agricultura americano e a base é utilizada para realizar testes de desempenho em sistemas gerenciadores de banco de dados. O arquivo de dump (usda.sql) pode ser dividido em duas partes. A primeira contém as rotinas de criação de 10 tabelas (CREATE TABLE…) e a segunda parte contém as rotinas de inserção nas tabelas (COPY data…).

O trabalho deve implementar uma estrutura de dados que permita definir campos e inserir valores na estrutura. Esta estrutura deve permitir a indexação (ou seja, inserção de chaves primárias). A estrutura não precisa tratar restrições e tipos de dados SQL. Por exemplo, na definição da tabela data_src há campos NOT NULL, text, character, etc. Não é preciso fazer análise dessas restrições e, para simplificar, considere que todos os campos são strings (mas se você criar uma solução em que pode-se informar se o campo armazena numéricos ou strings, muito melhor – faz muita diferença pra busca!). Não será necessário ler as definições de estruturas do arquivo SQL. Crie uma estrutura de dados e depois, em código, defina as 10 instâncias dessa estrutura.

Neste trabalho, cabe ao aluno definir da melhor forma sua estrutura, pensando em gasto de memória e desempenho. Questões de desempenho serão melhor exploradas na segunda parte do trabalho. Para facilitar a compreensão, pense, por exemplo, numa estrutura que pudesse ser definida como da forma abaixo (novamente, é apenas um exemplo. Você pode pensar em soluções melhores):

```
Database b = new Database();
String[] camposT1 = {“datasrc_id”, “authors”, “title”, “year”, “journal”};
Table t1 = new Table(“data_src”, camposT1) ;
b.addTable(t1);
String[] registroT1 = {“D1066”, “G.V. Mann”, “The Health and Nutritional”, “1962”, “American Journal of Clinical Nutrition”};
b.put(“data_src”, registroT1);
```

Assim, não importa para o usuário do seu programa se as classes Database e Table estão implementando listas contíguas, listas encadeadas, árvores, etc.

O principal uso de banco de dados é permitir uma consulta rápida sobre os dados contidos no banco. Para tal, SGBDs implementam estruturas de dados de busca que indexam um campo (ou conjunto de campos), sendo estas chaves primárias. Para organizar estes dados em memória, é possível usar inúmeras estruturas de dados. Projete uma solução fazendo uso de estruturas de dados de forma que seja a melhor solução para o problema.

O aluno deverá montar um relatório explicando sua solução, mostrando graficamente sua estrutura implementada e realizar análise de desempenho da sua solução (coletando tempo para inserir todos os registros e o tempo para buscar todos os registros). Por fim, o relatório deverá analisar a complexidade da sua solução em relação ao gasto de memória e nas operações de busca e inserção.

## Funcionalidades a serem implementadas
1. O sistema deve permitir instanciar quantas tabelas o usuário quiser (via código, mas se conseguir fazer lendo de um arquivo, melhor). Ao instanciar a tabela, é necessário considerar que exista um campo de chave primária para indexação. De forma a simplificar, as chaves podem ser sempre atômicas (embora no nosso benchmark existam chaves compostas. Se conseguir pensar numa solução que comporte chaves compostas, melhor).
2. O sistema deve ler o arquivo de dump do USDA e inserir cada registro na sua respectiva tabela.
3. O sistema deverá permitir, nesta versão, um único tipo de consulta:
  
    a. Dado um nome de tabela e um identificar, retornar os campos e valores do registro.

## Entrega
Este é um trabalho estritamente individual, então cuidado com códigos iguais.

Envie para o e-mail jairo.souza@ice.ufjf.br o relatório e o código-fonte (não envie arquivos executáveis no zip). Para evitar problemas, coloque teu código num serviço de nuvem e me envie apenas o link para baixá-lo.

Pode implementar em C, C++, Java, Scala ou Python. Caso use outra linguagem, consulte o professor antes de implementar. O seu código tem que rodar em outros computadores, então cuidado com valores de diretórios hardcoded ou outras soluções pouco portáveis.

Se preocupe em criar códigos legíveis. A nota será atribuída de acordo com o que eu entender do código. Então, quanto mais claro o código estiver, melhor.

## Pontuação
Além disso, será avaliado: Relatório, pontualidade, Solução e Completude 

O trabalho será avaliado em 5 dimensões: completude/corretude, pontualidade, qualidade do código, qualidade da solução e relatório. Os pesos de cada dimensão serão definidos posteriormente. Dentre os critérios, estão:

- Completude/Corretude: o sistema deve ter todas as funcionalidades pedidas e retornar os resultados esperados. Caso o programa não funcione, a nota será zero nesta dimensão. O professor irá rodar o programa com os arquivos de teste.
- Pontualidade (quanto mais atrasada for a entrega do trabalho, mais pontos o trabalho perde)
- Relatório bem redigido. Experimentos e discussão bem fundamentada.
- Qualidade do código: Código documentado e boa prática de programação (o mínimo necessário de variáveis globais, variáveis e funções com nomes de fácil compreensão, soluções elegantes de programação, código bem modularizado, etc). Boas soluções para melhorar o desempenho do sistema (tempo e/ou espaço).
- Qualidade da solução: Embora o sistema seja completo e correto, não quer dizer que ele é bom, não é? Se esforçar para explorar a melhor solução para o problema é essencial.
