# Tracked Car
Projetos para a Disciplina de Internet of Things.<br>
Alunos: Ramon Maciel  e Randler Ferraz <br>
----------------------------------------------

#Descrição do Projeto

**SISTEMA DE SEGURANÇA PARA CARRO →** O sistema deve ser responsável por notificar ao usuário ond eo seu carro foi visto pela ultima vez.<br>
O usuário assim que ter o carro roubado poderá ele mesmo notificar que seu carro foi roubado pelo seu celular. Assim que um de nossos leitores identificar o carro enviará um e-mail de alerta e uma notificação par ao seu celular com a Foto do veículo, Data e Hora que foi visto e localização.<br>

Os seguintes sensores serão utilizados para implementação desse sistema embarcado e alto nível.

* Deverá ser desenvolvido um SERVIÇO WEB RESTful (utilizando a linguagem JAVA e a API Jersey) <br>
sobre uma instância de servidor TOMCAT. O Serviço Web deve estar integrado ao Driver de forma que<br>
consiga obter as últimas informações lidas a partir dos sensores;<br>

**O DRIVER deve conter as seguintes características:** <br>
* (i) ele deve ser capaz de ser iniciado e finalizado por um script de linha de comando (INIT.D);<br> 
* (ii) ele deve ser capaz de se comunicar com o Serviço Web através de um arquivo PIPE;<br>
* (iii)Deve ser possível testar todas as saídas de dados do Serviço Web através de um Browser;<br>

**O Serviço Web deve ser capaz de fornecer para aplicações-cliente:** 
* (i) os dados lidos dos sensores na forma como são repassados pelo driver;<br>
* (ii) diagnósticos sobre os dados lidos dos sensores <br>

**Criação de um Cliente de consumo das informações lidas a partir do Serviço Web**<br>
Este cliente deve utilizar o Serviço Web para exibir os valores (dos sensores) lidos e os diagnósticos realizados na forma<br>
de uma Aplicação.

