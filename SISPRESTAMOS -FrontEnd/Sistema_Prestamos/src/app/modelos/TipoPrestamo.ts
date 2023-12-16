import { Prestamo } from "./Prestamo";

export class TipoPrestamo {
    id: number = 0;
    descripcion: string = "";
    tasa: number = 0;
    prestamos:Prestamo[]=[];
}
