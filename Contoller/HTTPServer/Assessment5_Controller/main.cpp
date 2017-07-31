#include "mbed.h"
#include "C12832.h"  /* for the LCD */
#include "rtos.h"
#include "HTTPServer.h"
#include "rpc.h"
#include "EthernetInterface.h"
#include "RPCVariable.h"

DigitalOut lr(PTB22), lg(PTE26), lb(PTB21), xr(D5), xg(D9), xb(PTC12);
//rpcDigitalOut led1(LED1,"led1");

EthernetInterface eth;
HTTPServer svr;

Serial xbee(D1, D0);
Serial pc(USBTX, USBRX); 

C12832 lcd (D11, D13, D12, D7, D10);   /* LCD */

DigitalIn fire(D4);

int blueLED, redLED, greenLED, request, device, open = 0;

bool alarm, xbeeFound = false;  
char c,g, tempc, tempg;

RPCVariable<int> rpc_device(&device,"Device");
RPCVariable<int> rpc_open(&open,"Open");

void offLED() { wait(0.4f); lr = lb = lg = xr = xg = xb = 1.0; }
void togglelg() { lg = !lg; } 
void togglelr() { lr = !lr; }
void togglexg() { xg = !xg; }
void togglexr() { xr = !xr; }
void togglexb() { xb = !xb; }
void clearLCD() { lcd.cls(); lcd.locate(12,1); }



bool openDoor() 
{
    lcd.locate(15,12);
    lcd.printf("Door is open!"); 
    togglelr();
    open = 1; 
    if (fire) { return false; }
    else { return true; }
}

void closeDoor()
{
    open = 0; 
    togglelg();
    lcd.locate(15,12);
    lcd.printf("Door is closed."); 
}

void deviceID() 
{
    togglexg();  
}

bool gMenu(char x) {
    lcd.locate(18,20); 
    lcd.printf("Device : ");
    switch (x) {
        case '1' : { 
            lcd.printf(": 1");
            device = 1; 
            return true;
        }
        case '2' : {
            lcd.printf(": 2");
            device = 2; 
            return true;
        }
        default : break;  
    }
    togglexr(); 
    offLED();
    return false;
}

void cMenu(char c) {
    clearLCD();
    lcd.locate(5,1);
    lcd.printf("XBee Device says : ");
    switch (c) {
        case 'R' : { lr = 0.0; lg= 1.0; lb = 1.0; offLED(); }
        case 'G' : { lr = 1.0; lg= 0.0; lb = 1.0; offLED(); }
        case 'B' : { lr = 1.0; lg= 1.0; lb = 0.0; offLED(); }
        case 'S' : {
            while (pc.readable()) { 
                //pc.gets(buf, 256);
                //rpc(buf, outbuf);
                //pc.printf("%s\n", outbuf); 
            }
        }
        case 'O' : { 
            alarm = true; // change this - web variable  
            while (alarm) {
                alarm = openDoor(); 
                g = xbee.getc(); 
                xbeeFound = gMenu(g); 
                offLED(); 
                if (fire) {
                    alarm = false;
                    clearLCD();
                    lcd.printf("Alarm has been reset.");
                }
            }
        }
        case 'C' : { 
            closeDoor();
            g = xbee.getc(); 
            xbeeFound = gMenu(g);    
        }
        default : break;  
    }
}

int main()
{          
    offLED();
    
    //RPC::add_rpc_class<RpcVariable>();
    
    printf("Setting up...\n");
    eth.init();
    int ethErr = eth.connect();
    printf("IP Address is %s\n", eth.getIPAddress());
    if (ethErr < 0)
    {
        printf("Error %d in setup.\n", ethErr);
        lcd.printf("Error %d in setup. \n", ethErr);
        //return -1;
    }
    
    // svr handlers
    svr.addHandler<HTTPRpcRequestHandler>("/rpc");
    
    svr.start(80, &eth); //attach server to port 80
    printf("Listening...\n");
    
    Timer tm;
    tm.start();
    // Listen indefinitely
    clearLCD();   
    lcd.locate(1,1);
    lcd.printf("Welcome. No readable xbee."); 
        
    while (true) {
        svr.poll();
        if(tm.read()>.5)
        {
            tm.start();
        }
        if (xbee.readable()) 
        {
            c = xbee.getc();
            cMenu(c); 
        }
        offLED();
    }        
}
