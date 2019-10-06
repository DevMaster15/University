#include <stdio.h>
#include <math.h>
#include "functions.h"

//heapSize = 200
//maxHeap = indirizzo dell'array iniziale con gli elementi già memorizzati al suo interno
void buildMaxHeap(int *maxHeap, int heapSize, int dim_array){

    for(int i=floor(dim_array/2 - 1);i>=0;i--){
        maxHeapify(maxHeap, i, heapSize); //richiamo maxHeapify per mettere apposto il nodo i se c'è necessità
    }

    
}
