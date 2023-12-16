import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminLayoutComponent } from './admin-layout/admin-layout.component';
import { MantenimientoJefeComponent } from './componentes/mantenimiento-jefe/mantenimiento-jefe.component';
import { MantenimientoPrestamistaComponent } from './componentes/mantenimiento-prestamista/mantenimiento-prestamista.component';
import { ListadoJefeComponent } from './componentes/listado-jefe/listado-jefe.component';
import { ListadoPrestamistaComponent } from './componentes/listado-prestamista/listado-prestamista.component';
import { RecuperarComponent } from './login/recuperar/recuperar.component';
import { RegistrarComponent } from './login/registrar/registrar.component';
import { JefeLayoutComponent } from './jefe-layout/jefe-layout.component';
import { SolicitudComponent } from './componentes/solicitud/solicitud.component';
import { ListarCuentaComponent } from './componentes/listar-cuenta/listar-cuenta.component';
import { IngresarCuentaComponent } from './componentes/ingresar-cuenta/ingresar-cuenta.component';
import { PagoCuotaComponent } from './componentes/pago-cuota/pago-cuota.component';
import { VerEstadoPrestamoComponent } from './componentes/ver-estado-prestamo/ver-estado-prestamo.component';
import { ListarSolicitudComponent } from './componentes/listar-solicitud/listar-solicitud.component';
import { AprobarSolicitudComponent } from './componentes/aprobar-solicitud/aprobar-solicitud.component';
import { VerSolicitudClienteComponent } from './componentes/ver-solicitud-cliente/ver-solicitud-cliente.component';
import { VerCuotasPorPrestamistaComponent } from './componentes/ver-cuotas-por-prestamista/ver-cuotas-por-prestamista.component';
import { RegistrarPagoComponent } from './componentes/registrar-pago/registrar-pago.component';
import { VerPrestamosPorPrestamistaComponent } from './componentes/ver-prestamos-por-prestamista/ver-prestamos-por-prestamista.component';
import { ListadoCuotas } from './componentes/listado-cuota/listado-cuota.component';
import { VerFinanzasComponent } from './componentes/ver-finanzas/ver-finanzas.component';
import { VerCuotasPorJefeComponent } from './componentes/ver-cuotas-por-jefe/ver-cuotas-por-jefe.component';



const routes: Routes = [
  { path: 'admin' , component: AdminLayoutComponent, 
    children:[
    { path: 'dashboard', component: DashboardComponent }, 
    { path: 'mantenimiento-jefe/registrar', component: MantenimientoJefeComponent },
    { path: 'mantenimiento-jefe/actualizar/:actualizar', component: MantenimientoJefeComponent },
    { path: 'pago-cuota', component: PagoCuotaComponent },
    { path: 'listado-jefe', component: ListadoJefeComponent },
    { path: 'listado-prestamista', component: ListadoPrestamistaComponent },
    { path: 'ver-estado-prestamo', component: VerEstadoPrestamoComponent },
    { path: 'mantenimiento-prestamista/actualizar/:actualizar', component: MantenimientoPrestamistaComponent},
    { path: 'mantenimiento-prestamista/registrar/:registrar', component: MantenimientoPrestamistaComponent},
    { path: 'solicitud-ingresar',component:SolicitudComponent},
    { path: 'aprobar-solicitud/:idSolicitud', component: AprobarSolicitudComponent },
    { path: 'listar-solicitud',component:ListarSolicitudComponent},
    { path: 'listar-cuenta',component:ListarCuentaComponent},
    { path: 'ingresar-cuenta',component:IngresarCuentaComponent},
    { path: 'ingresar-cuenta/:idCuenta',component:IngresarCuentaComponent},
    { path: 'ver-cuotas-por-prestamista/:idPrestamo',component:VerCuotasPorPrestamistaComponent},
    { path: 'ver-cuotas-por-jefe/:idPrestamo',component:VerCuotasPorJefeComponent},
    { path: 'ver-prestamos-por-prestamista',component:VerPrestamosPorPrestamistaComponent},
    { path: 'ver-finanzas',component:VerFinanzasComponent},
    { path: 'registrar-pago/:actualizar',component:RegistrarPagoComponent},
    { path: 'solicitud', component:VerSolicitudClienteComponent},
    { path: 'listado-cuota', component: ListadoCuotas }
  ] },
  { path: '', redirectTo:'/login', pathMatch: 'full'},
  { path: 'login', component: LoginComponent },
  {path: 'recuperar',component:RecuperarComponent},
  {path: 'registrar',component:RegistrarComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes)],
  exports: [RouterModule],

})
export class AppRoutingModule { }

