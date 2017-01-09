/**
 ===========================================================
 Created by James Tang
 ID: 260685449 
 Desciption: Turning command line arguments into a string and 
             display historgram. 
 
 
 ===========================================================
 **/
#include <stdio.h>
#include <stdlib.h>

// the below function defintions can be included in the header file

void append(char out[], char in[]);
void toString(int argc, char *argv[], char buffer[]);
int  doHist(char buffer[], char hist[]);
void displayHist(char hist[],int distinct_chars);
int lengthCounter(char charCounted[]);

// header file ends

int main(int argc, char *argv[]){
    char buffer[150];
    char myHist[256];
    toString(argc,argv,buffer);
    int distinct_characters= doHist(buffer,myHist);
    printf("%s",myHist);
    displayHist(myHist,distinct_characters);
}

int  doHist(char buffer[], char hist[]){
    int i;
    for(i=0;i<256;i++){
        hist[i]=0;
    }
    int j;
    for(j=0;j<lengthCounter(buffer);j++){
        char c=buffer[j];
        int k=c;
        
        hist[k]=hist[k]+1;
    }
    int counter=0;
    for(int m=0;m<256;m++){
        if(hist[m]!=0){
            counter++;
        }
        
    }
    return counter;
}

void displayHist(char hist[],int distinct_chars){
    printf("%d %s\n",distinct_chars,"distinct characters found");
    int numbuffer=0;
    int max;
    int position=0;
    for (int i=1;i<255;i++){
        if(numbuffer<hist[i]){
            numbuffer=hist[i];
            max=hist[i];
            position=i;
        }else{
            max=numbuffer;
            
        }
    }

    int counter;
    for (counter=1;counter<256;counter++){
        if(hist[counter]!=0){
            int barlength =(int)(((double)hist[counter])/((double)max)*((double)25));
            char dot[25];
            int i=0;
            for (i=0;i<25;i++){
                if(i<barlength){
                dot[i]='*';
                }else{
                    dot[i]='\0';
                }
            }
            dot[i+1]='\0';
            char c=counter;
            printf("%c %s %d %s %s \n",c,"[",hist[counter],"]",dot);
            
        }
    }

}

int lengthCounter(char charCounted[]){
    int length=0;
    for(int counter=0; charCounted[counter]!='\0';counter++){
        length=length+1;
    }
    
    return length;
}

void toString(int argc, char *argv[], char buffer[]){
    buffer[0]='\0';
    
    for (int i=1; i<argc; i++) {
        append(buffer,argv[i]);
        
    }
    
    
}

void append(char out[],char in[]){
    int i,j;
    i=0;
    while(out[i]!='\0'){
        i++;
    }
    j=0;
    while(in[j]!='\0')
    {
        out[i+j]=in[j];
        j++;
    }
    
        out[i+j]='\0';
}

