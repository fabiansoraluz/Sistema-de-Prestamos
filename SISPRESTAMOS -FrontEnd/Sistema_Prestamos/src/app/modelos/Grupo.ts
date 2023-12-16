import { Prestamista } from './Prestamista';
import { Prestamo } from './Prestamo';
import { Solicitud } from './Solicitud';
import { Ubigeo } from './Ubigeo';
import { Usuario } from './Usuario';

export class Grupo {
    id: number = 0;
    estado:number = 0;
    create_at:string ="";
    jefe:Usuario;
    prestamistas:Prestamista;
    ubigeo:Ubigeo = new Ubigeo();

}
