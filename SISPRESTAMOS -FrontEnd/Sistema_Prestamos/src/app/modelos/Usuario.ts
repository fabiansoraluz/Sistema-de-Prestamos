import { Rol } from './Rol'
import { Persona } from './Persona';


export class Usuario {
    id: number = 0
    username: string = "" 
    password: string  = ""
    image: string ="test";
    correo: string = ""
    create_at: string = ""
    estado: number = 0
    rol:Rol = new Rol();
    persona:Persona = new Persona();
}