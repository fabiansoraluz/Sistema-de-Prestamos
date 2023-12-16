import { Cuota } from './Cuota'; 
import { FormaPago } from './FormaPago';

export class PagoCuota {
    id: number = 0;
    monto: number = 0;
    fecPago: string = "";
    fecVencimiento: string = "";
    estado: number = 0;
    createAt: string = "";
    cuota: Cuota | null = null;
    formaPago: FormaPago | null = null;
}
