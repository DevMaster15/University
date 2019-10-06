#include <stdio.h>
#include <stdlib.h>
#include "functions.h"
#include "buildmaxheap.h"

#define N 200



int main()
{
    int *array; //dichiarazione array mediante puntatore
    int dim_array;
    int heap_size = N; //numero totale di possibili valori che posso inserire nella maxHeap

    array = (int*) malloc(N*sizeof(int)); //allochiamo all'array 200 inidirizzi di default

    printf("Inserisci la dimensione dell'array: ");
    scanf("%d", &dim_array);

    /*
    for(int j=dim_array;j<=200;j++){
        free(j);
    }
    */

    inserisci_elementi(array, dim_array);
    buildMaxHeap(array, heap_size, dim_array);

    printf("MAX HEAP: \n");
    stampa(array, dim_array);

}

void inserisci_elementi(int *ptr, int dim_array){

    //inserimento degli elementi nell'array
    for(int i=0;i<dim_array;i++){
        scanf("%d", (ptr+i));
    }

}

void stampa(int *ptr, int dim_array){
    for(int i=0;i<dim_array;i++){
        printf("%d \n", *(ptr+i));
    }
}
