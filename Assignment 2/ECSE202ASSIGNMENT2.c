
// defintion of constant data and include
// standard input/output library

#define NR 50
#define MAXNAMELENGTH 15
#include <stdio.h>
#include <stdlib.h>

//  swapping sturcture

// Building a struct with name SR
struct SR
{
    char p[50];
    char q[15];
    int r;
    int s;
};

// function declaration below
// Swap function that swaps two structures
void swap(struct SR *pa,struct SR *pb);
void quicksort(struct SR rl[], int i,int j);
int binarySearch(struct SR rl[],int ID,int mid_pos);
int paritition(struct SR rl[], int left,int right,int pivot);
// function dellaration end


int main(int argc, char * argv[]) {
    
    struct SR SR[NR];
    int RN;
    
    //Read in Names and ID data
    FILE * DF;
    if((DF = fopen(argv[1], "r")) == NULL){
        printf("Can't read from file %s\n", argv[1]);
        exit(1);
    }
    for (RN=0;RN<NR;RN++){
        fscanf (DF, "%s%s%d", &(SR[RN].p[0]),&(SR[RN].q[0]),&(SR[RN].r));
    }
    fclose(DF);
    
    //Read in s data
    FILE * MDF;
    if((MDF = fopen(argv[2], "r")) == NULL){
        printf("Can't read from file %s\n", argv[2]);
        exit(1);
    }
    // use loop to print out data
    for (RN=0;RN<NR;RN++){
        fscanf (MDF, "%d", &(SR[RN].s));
    }
    fclose(MDF);
    
    // Print the unsorted records
    for(RN=0;RN<NR;RN++){
        printf("%s %s %d %d\n",SR[RN].p,SR[RN].q,SR[RN].r, SR[RN].s);
    }
    printf("\n");
    printf("\n");
    
    
    quicksort(SR,0,49);
    
    //Print out sorted data
    printf("%s\n","Records sorted by ID");
    for(RN=0;RN<NR;RN++){
        printf("%s %s %d %d\n",SR[RN].p,SR[RN].q,SR[RN].r, SR[RN].s);
    }
    // print out two new lines
    printf("\n");
    printf("\n");
    
    // use atoi to convert character input to integer type
    int numID=atoi(argv[3]);
    binarySearch(SR,numID,26);
    return EXIT_SUCCESS;
}

// input a structure array. ID number from command line. And set initial mid_pos as 26
int binarySearch(struct SR rl[],int ID,int mid_pos){
    while(mid_pos!=1&&mid_pos!=49)
    {
    if(rl[mid_pos].r<ID){
        mid_pos=mid_pos/2+mid_pos;
        // binary search shifting to the right section
    }else if(rl[mid_pos].r>ID){
        mid_pos=mid_pos/2;
        // binary search shifting to the left section
    }else if((rl[mid_pos].r=ID)){
        break;
    }}
    // check if ID exists
    if (ID==rl[mid_pos].r){
    
    // print the search result
    printf("%s%d%s%s%s%s%s%d\n","ID: ",rl[mid_pos].r," Student: ",rl[mid_pos].q,",",rl[mid_pos].p," Mark: ",rl[mid_pos].s);
    }else{
        printf("%s%d%s\n","ID: ",ID," dose not exist");
    }
    return 0;
}

// quick sort takes in an array of struct with type SR
void quicksort(struct SR rl[], int left,int right){

    if(right-left<=0){
        // terminate the function if right overlapses the left
        return;
    }else{
        int pos_pivot=rl[right].r;
        int partition_point=paritition(rl, left,right, pos_pivot);
        // Use recursion to recall the function from within
        quicksort(rl, left, partition_point-1);
        quicksort(rl, partition_point+1, right);
        
    }

}

// partitition takes in an array of struct with type SR
int paritition(struct SR rl[], int left,int right,int pivot){
    int left_pointer=left-1;
    int right_pointer=right;
    while(5!=0){
        while(rl[++left_pointer].r<pivot){
        // do nothhing
        }

        while((right_pointer>0)&&(rl[--right_pointer].r>pivot)){
        // do nothing

        }

        if (left_pointer>=right_pointer){
            
            break;
        }else{
            // use below to test the output from partition
            // printf("%s%s\n",rl[left_pointer].p, rl[right_pointer].p);
            swap(&rl[left_pointer], &rl[right_pointer]);
            // printf("%s%s\n",rl[left_pointer].p, rl[right_pointer].p);
        }
    }
        swap(&(rl[left_pointer]),&(rl[right]));
        return left_pointer;
}


void swap(struct SR *pleft, struct SR *pright) {
    // create a temporary place to hold the data
    struct SR temp;
    temp=*pleft;
    // dereference the pointer
    *pleft=*pright;
    *pright=temp;
}

