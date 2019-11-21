void inserisci_elementi(int *ptr, int dim_array);

//procedura che "fa scivolare" il nodo i, in basso se è maggiore del suo genitore
void maxHeapify(int *maxHeap, int i, int heapSize); 

//ritorno il figlio sinistro di i, ovvero 2*i + 1
int left(int i);

int right(int i);

void buildMaxHeap(int *maxHeap, int heapSize, int dim_array);

void swap(int *position_1, int *position_2);

void stampa(int *ptr, int dim_array);