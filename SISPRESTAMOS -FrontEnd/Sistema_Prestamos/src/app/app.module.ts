import { Component, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HeaderComponent } from './header/header.component';
import { SideNavComponent } from './side-nav/side-nav.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminLayoutComponent } from './admin-layout/admin-layout.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MantenimientoJefeComponent } from './componentes/mantenimiento-jefe/mantenimiento-jefe.component';
import { MantenimientoPrestamistaComponent } from './componentes/mantenimiento-prestamista/mantenimiento-prestamista.component';
import { ListadoJefeComponent } from './componentes/listado-jefe/listado-jefe.component';
import { ListadoPrestamistaComponent } from './componentes/listado-prestamista/listado-prestamista.component';

import {MatInputModule} from '@angular/material/input';
import {NgClass, NgFor, NgIf} from '@angular/common';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgxPaginationModule } from 'ngx-pagination';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { AuthService } from './services/auth.service';
import { TokenService } from './services/token.service';
import { RecuperarComponent } from './login/recuperar/recuperar.component';
import { RegistrarComponent } from './login/registrar/registrar.component';
import { UtilesService } from './services/utiles.service';
import { JefeLayoutComponent } from './jefe-layout/jefe-layout.component';
import { errorTailorImports, provideErrorTailorConfig } from '@ngneat/error-tailor';
import { SolicitudComponent } from './componentes/solicitud/solicitud.component';
import { ListarCuentaComponent } from './componentes/listar-cuenta/listar-cuenta.component';
import { IngresarCuentaComponent } from './componentes/ingresar-cuenta/ingresar-cuenta.component';
import { PagoCuotaComponent } from './componentes/pago-cuota/pago-cuota.component';
import { VerEstadoPrestamoComponent } from './componentes/ver-estado-prestamo/ver-estado-prestamo.component';
import { VerSolicitudClienteComponent } from './componentes/ver-solicitud-cliente/ver-solicitud-cliente.component';
import { ListarSolicitudComponent } from './componentes/listar-solicitud/listar-solicitud.component';
import { AprobarSolicitudComponent } from './componentes/aprobar-solicitud/aprobar-solicitud.component';
import { VerCuotasPorPrestamistaComponent } from './componentes/ver-cuotas-por-prestamista/ver-cuotas-por-prestamista.component';
import { RegistrarPagoComponent } from './componentes/registrar-pago/registrar-pago.component';
import { VerPrestamosPorPrestamistaComponent } from './componentes/ver-prestamos-por-prestamista/ver-prestamos-por-prestamista.component';
import { ListadoCuotas } from './componentes/listado-cuota/listado-cuota.component';
import { VerFinanzasComponent } from './componentes/ver-finanzas/ver-finanzas.component';
import { VerCuotasPorJefeComponent } from './componentes/ver-cuotas-por-jefe/ver-cuotas-por-jefe.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SideNavComponent,
    LoginComponent,
    DashboardComponent,
    AdminLayoutComponent,
    MantenimientoJefeComponent,
    MantenimientoPrestamistaComponent,
    ListadoJefeComponent,
    ListadoPrestamistaComponent,
    RecuperarComponent,
    RegistrarComponent,
    JefeLayoutComponent,
    SolicitudComponent,
    ListarCuentaComponent,
    IngresarCuentaComponent,
    PagoCuotaComponent,
    VerEstadoPrestamoComponent,
    VerSolicitudClienteComponent,
    ListarSolicitudComponent,
    AprobarSolicitudComponent,
    VerCuotasPorPrestamistaComponent,
    RegistrarPagoComponent,
    VerPrestamosPorPrestamistaComponent,
    ListadoCuotas,
    VerFinanzasComponent,
    VerCuotasPorJefeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    NgFor, NgIf,
    MatSelectModule,
    MatFormFieldModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    NgxPaginationModule,
    errorTailorImports,
  ],
  providers: [
    AuthService,
    TokenService,
    UtilesService,
    provideErrorTailorConfig({
      errors: {
        useValue: {
          required: 'Campo obligatorio',
          minlength: ({ requiredLength, actualLength }) => 
                      `Se espera ${requiredLength} caracteres pero tienes ${actualLength}`,
          maxlength: ({ requiredLength, actualLength }) => 
          `Se espera ${requiredLength} caracteres pero tienes ${actualLength}`,
          min: 'El valor debe ser mayor',
          pattern:'Debes seguir el formato',
          invalidAddress: error => `Address isn't valid`,
          email:'Ingresar un formato de correo válido',
          dateInvalid:'Ingresar una fecha valida'
        }
      }
    })
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }

