import { PagoCuota } from './PagoCuota'; 
import { Prestamo } from './Prestamo'; 

export class Cuota {
    id: number = 0;
    numero: string = "";
    monto: number = 0;
    fecPago: string = ""; 
    estado: number = 0;
    montoPagado: number = 0;
    pago: PagoCuota | null = null;
    prestamo: Prestamo | null = new Prestamo();
}
