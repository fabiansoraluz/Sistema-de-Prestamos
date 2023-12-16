import { Usuario } from './Usuario';
import { Grupo } from './Grupo';

export class Prestamista {
    id: number
    grupo:Grupo = new Grupo();
    usuario:Usuario = new Usuario();
}
