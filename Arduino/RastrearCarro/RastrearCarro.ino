#include <Wire.h>
#include <SPI.h>
#include <MFRC522.h>
#include "RCSwitch.h"

//intancia do emissor e do receptor
RCSwitch emissor = RCSwitch();
RCSwitch receptor = RCSwitch();

//definindo portas do RFID
#define SS_PIN 10
#define RST_PIN 9

//diretivas de compilação -> escolhe qual sera usada DEMON/MIDDLEWARE
//#define MIDDLEWARE
#define DAEMON

// Definicoes pino modulo RC522
MFRC522 mfrc522(SS_PIN, RST_PIN); 

//Total de controles de RF possiveis
#define RFID_LIMITE_INFER 0
#define RFID_LIMITE_SUPER 100

//Deslocamento de variaveis
#define DESLOCAMENTO_RFID 16


//definindo qual será esse RF -> Radio Frequencia
#define RFID 6

//--------------------- Struct DAEMON ou MIDDLEWARE ------------------------------------------------
#ifdef MIDDLEWARE
struct InfoRF{
  int id;
  int rfid;
} infoRF;
#endif

#ifdef DAEMON
  long infoRF;
#endif

// ------------------------ SETUP -------------------------
void setup() 
{
  Serial.begin(9600);
  SPI.begin();
  // Inicia  emissor e receptor de radiofrequencia
  emissor.enableTransmit(4);
  receptor.enableReceive(0); 
  // Inicia MFRC522
  mfrc522.PCD_Init(); 
}

//------------------------------ LOOP ---------------------------
void loop() 
{
  long info = 0;
  info = lerSensores();
  
  emitir(info);
  
  delay(500);
  
  info = receber();

  if (info != -1) {
    if (IDValido(info)) {
            
#ifdef MIDDLEWARE      
    infoRF.rfid = extrairUid(info);
#endif

#ifdef DAEMON 
    infoRF = info;
#endif  
    enviarParaUSB();
    }
  }
  delay(1000);
}


/***************************** METODOS **************************
/*  Daqui para baixo estao os procedimentos utilizados
 * para realizar a codificação
 * Program: Captura de Tag RFID e envio para RF(radio Frequencia) com controle de informações em computador
 * Auth: Ramon Maciel e Randler Ferraz
 */

//-------------------------------- RFIDValido ---------------------------------
boolean IDValido(long rf) {
  boolean valido = false;

#ifdef MIDDLEWARE
  infoRF.id = rf  >> DESLOCAMENTO_RFID;
  if((infoRF.id >= RFID_LIMITE_INFER) &&
      (infoRF.id <= RFID_LIMITE_SUPER)){
    valido = true;
  }
#endif

#ifdef DAEMON
  long id = rf  >> DESLOCAMENTO_RFID;
  if((id >= RFID_LIMITE_INFER) &&
      (id <= RFID_LIMITE_SUPER)){
    valido = true;
  }
#endif
  return valido;
}
// -------------------------- Pegar Dados RFID ---------------
int getUID() {
 verificarLeitorRFID();
 int uid = mfrc522.uid.uidByte[0];
 return uid;
}
//------------------------- VERIFICAR LEITOR RFID ---------------
void verificarLeitorRFID() {
// Aguarda a aproximacao do cartao
  if ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }
  // Seleciona um dos cartoes
  if ( ! mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }
}

//------------------------------- lerSensores ----------------------------------------
long lerSensores() {

  long uid = getUID();  

  long rf = RFID;  
  long info = rf << DESLOCAMENTO_RFID;
  info = info | uid;
  return info;
}

//------------------------------ EMITIR -------------------------
void emitir(long info) {
  emissor.send(info, 32);
}

//------------------------------- Receber ------------------------------------------- 
long receber() {
  long info = -1;
  
  if (receptor.available()) {
    info = receptor.getReceivedValue();
    receptor.resetAvailable();
  }  
  return info;
}

//---------------------------- Enviar para USB ----------------------------------------------
//metodo para enviar
void enviarParaUSB(){
#ifdef MIDDLEWARE
   char buff[sizeof(InfoRF)] = {0};   
   memcpy(&buff, &infoRF, sizeof(InfoRF));
   
   Serial.write('I');
   Serial.write((uint8_t*) &buff, sizeof(InfoRF));
   Serial.write('F');
#endif

#ifdef DAEMON
   Serial.write('I');
   Serial.write((uint8_t*) &infoRF, sizeof(infoRF));
   Serial.write('F');
#endif   
}

// ----------------------------- Metodos de Extração de valores ----------------------------
#ifdef MIDDLEWARE
int extrairUid(long info) {
  int uid = (info & 65535);
  return uid;
}
#endif


