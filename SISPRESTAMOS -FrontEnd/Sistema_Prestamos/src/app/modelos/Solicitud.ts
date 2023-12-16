import { Cliente } from './Cliente';
import { CuentaBancaria } from './CuentaBancaria';
import { Grupo } from './Grupo';

export class Solicitud {
    id: string
    dias:number
    monto: number
    observacion: string 
    fechaSolicitud: Date
    estado: number 
    cliente:Cliente = new Cliente();
    cuenta:CuentaBancaria = new CuentaBancaria();
    grupo:Grupo = new Grupo();
}
