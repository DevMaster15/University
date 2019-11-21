#include <stdio.h>
#include "functions.h"

void maxHeapify(int *maxHeap, int i, int heapSize){
    
    int longer_element = 0;

    int left_node = left(i);
    int right_node = right(i);

    //controllo se i figli di i, sono entro il limite della maxHeap(200)

    //controllo se il figlio sinistro è maggiore del genitore
    //se lo è non va bene
    if(left_node < heapSize && *(maxHeap + left_node) > *(maxHeap+i)){
        longer_element = left_node;
    } else longer_element = i;

    //controllo se il figlio destro è maggiore del genitore
    //se lo è non va bene
    if(right_node < heapSize && *(maxHeap + right_node) > *(maxHeap+longer_element)){
        longer_element = right_node;
    }

    //se l'elemento più grande è rimasto i, tutto ok
    //sennè scambia i due elementi e richiama maxHeapify
    if(longer_element != i){
        swap(maxHeap+longer_element, maxHeap+i);
        maxHeapify(maxHeap, longer_element, heapSize);
    }
    
}

int left(int i){
    return 2*i + 1;
}

int right(int i){
    return 2*i+2;
}

void swap(int *a, int *b){

    int temp = *b;
    *b = *a;
    *a = temp;

}