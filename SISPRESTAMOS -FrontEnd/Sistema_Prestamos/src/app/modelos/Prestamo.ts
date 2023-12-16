import { TipoPrestamo } from './TipoPrestamo'; 
import { Prestamista } from './Prestamista'; 
import { Solicitud } from './Solicitud'; 
import { Cuota } from './Cuota'; 
import { Grupo } from './Grupo';

export class Prestamo {
    id: number = 0;
    monto: number = 0;
    montoPagado: number = 0;
    fecha_Inicio_prestamo: string = ""; 
    fecha_Fin_prestamo: string = ""; 
    tipoPrestamo = new TipoPrestamo();
    prestamista = new Prestamista();
    solicitud = new Solicitud();
    cuotas: Cuota[] = [];
    grupo:Grupo = new Grupo();
}
