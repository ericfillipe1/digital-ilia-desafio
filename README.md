# Desafio Ília   Backend | Spring Boot

Olá, tudo bem?

Você acaba de receber o convite para mais uma etapa do nosso processo seletivo.
Preparamos um pequeno desafio para conhecer um pouco mais dos seus
conhecimentos.

**#FICAADICA**: Siga as instruções abaixo:

✓ É mais importante qualidade de código do que o desafio 100% feito.

✓ Se não der tempo de fazer todas as funcionalidades, não tem problema. A ideia é
validar a solução de problemas, qualidade do código, organização, testes, arquitetura e
etc.

✓ Utilize Java 11

✓ Não utilize implementações como jHipster

✓ Lembre-se dos testes unitários

✓ Qualquer dúvida entre em contato conosco

Crie uma web API para controle de ponto, seguindo o contrato, que permite realizar as
seguintes ações:

**Registrar os horários da jornada diária de trabalho.**

● Apenas 4 horários podem ser registrados por dia.
● Deve haver no mínimo 1 hora de almoço.
● Sábado e domingo não são permitidos como dia de trabalho.

**Alocar horas trabalhadas, de um dia de trabalho, em um projeto.**

● Não pode alocar um tempo maior que o tempo trabalhado no dia.
● A soma do tempo de todas as alocações, referentes à um dia, não pode ser maior
que o tempo trabalhado no dia.

**Obter um relatório mensal que informe as horas trabalhadas, horas excedentes,
horas devidas, os horários registrados e o tempo alocado em cada projeto.**

● A jornada diária de trabalho é de 8 horas.
