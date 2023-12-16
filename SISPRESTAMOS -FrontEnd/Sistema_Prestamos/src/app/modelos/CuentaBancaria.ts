import { Cliente } from "./Cliente";
import { TipoCuenta } from "./TipoCuenta";

export class CuentaBancaria {
  id:number
  nombre:string
  moneda:string
  banco:String
  numero:string
  cliente: Cliente = new Cliente();
  tipo:TipoCuenta = new TipoCuenta();
}
  