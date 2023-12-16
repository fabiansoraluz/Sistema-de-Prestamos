import { Ubigeo } from "./Ubigeo";

export class Persona {
    id: number = 0;
    nombre: string= "";
    paterno: string = "";
    materno: string = "";
    celular: string = "";
    dni: string = "";
    ubigeo: Ubigeo = new Ubigeo();
}
