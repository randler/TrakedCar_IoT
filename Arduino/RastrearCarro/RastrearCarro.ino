 #include <Wire.h>
#include <SPI.h>
#include <MFRC522.h>

//definindo portas do RFID
#define SS_PIN 10
#define RST_PIN 9

//diretivas de compilação -> escolhe qual sera usada DEMON/MIDDLEWARE
//#define MIDDLEWARE
#define DAEMON

// Definicoes pino modulo RC522
MFRC522 mfrc522(SS_PIN, RST_PIN); 

//Deslocamento de variaveis
#define DESLOCAMENTO_RFID 16


//--------------------- Struct DAEMON ou MIDDLEWARE ------------------------------------------------
#ifdef MIDDLEWARE
struct InfoRF{
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

  mfrc522.PCD_Init(); 
}

//------------------------------ LOOP ---------------------------
void loop() 
{
  //aguarda até que passe alguma tag
    if ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }
  // Seleciona um dos cartoes
  if ( ! mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }
  
  long info = 0;
  info = lerSensores();

#ifdef MIDDLEWARE
  infoRF.rfid = info;
#endif

#ifdef DAEMON
  infoRF = info;
#endif
            
  
     
  enviarParaUSB();
  delay(200);
}


/***************************** METODOS **************************
/*  Daqui para baixo estao os procedimentos utilizados
 * para realizar a codificação
 * Program: Captura de Tag RFID e envio para RF(radio Frequencia) com controle de informações em computador
 * Auth: Ramon Maciel e Randler Ferraz
 */
// -------------------------- Pegar Dados RFID ---------------
long getUID() {
  String conteudo = "";
  
  for (byte i = 0; i < mfrc522.uid.size; i++) 
  {
     conteudo.concat(String(mfrc522.uid.uidByte[i]));
  }
  
 return conteudo.toInt();
}


//------------------------------- lerSensores ----------------------------------------
long lerSensores() {
  long uid = getUID();  
  long info = info | uid; 
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


