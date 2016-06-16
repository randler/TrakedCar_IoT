/*
 * Comunicacao.h
 *
 * Prove uma classe cujos metodos possibilitam
 * a comunicacao com um dispositivo controlador
 * que realiza a leitura de sensores de 10 eixos.
 *  Created on: 08/10/2015
 *      Author: Ramon Maciel
 */

#include <stdlib.h>
#ifndef COMUNICACAO_H_
#define COMUNICACAO_H_

#if _WIN32 || _WIN64
	#include <windows.h>


#endif

#ifdef __linux__
	#include <unistd.h>
	#define Sleep(x) usleep(x*1000);
#endif

class Comunicacao {
private :
		char* porta;


#ifdef __linux__
	int hPorta;
#endif
#if _WIN32 || _WIN64
	HANDLE hPorta;
#endif

public:

	Comunicacao(char* porta);
	//iniciar a comunicacao com a porta serial
	int iniciar();

	//realiza a leitura de um buffer a partir da porta serial
	int ler(char* buffer, long unsigned int bytsParaLer);

	//finalizar uso da porta serial
	int finalizar();


};








#endif /* COMUNICACAO_H_ */
