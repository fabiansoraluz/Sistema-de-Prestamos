import { Component } from '@angular/core';

@Component({
  selector: 'app-pago-cuota',
  templateUrl: './pago-cuota.component.html',
  styleUrls: ['./pago-cuota.component.css']
})
export class PagoCuotaComponent {
  cardNumber: string = '';
  networkName: string = '';
  cvc: string = '';
  expiry: string = '';
  cardholderName: string = '';
  cvcValid: boolean = false;
  expiryValid: boolean = false;
  cardNumberValid: boolean = false;
  

  validateCard() {
    if (this.cardNumber) {
      this.networkName = this.identifyCardNetwork(this.cardNumber);
      this.cardNumberValid = /^[0-9]{13,19}$/.test(this.cardNumber);
    } else {
      this.networkName = '';
      this.cardNumberValid = false;
    }
  }
  getCardImage(network: string): string {
    if (network === 'Visa') {
      return '../../../assets/img/visa.jpg'; // Reemplaza con la ruta de la imagen de Visa
    } else if (network === 'MasterCard') {
      return '../../../assets/img/mastercard.jpg'; // Reemplaza con la ruta de la imagen de MasterCard
    } else if (network === 'American Express') {
      return '../../../assets/img/american_express.jpg'; // Reemplaza con la ruta de la imagen de American Express
    } else {
      return ''; // En caso de una red desconocida o sin red
    }
  }
  validateCVC() {
    // Validar el CVC (por ejemplo, debe tener 3 )
    this.cvcValid = /^[0-9]{3}$/.test(this.cvc);
  }

  validateExpiry() {
    // Validar la fecha de vencimiento (MM/YY)
    this.expiryValid = /^[0-9]{2}\/[0-9]{2}$/.test(this.expiry);
  }

  processPurchase() {
    if (this.cvcValid && this.expiryValid && this.cardholderName && this.cardNumberValid) {
      // Realizar la compra (aquí debes implementar la lógica de procesamiento de la compra)
      console.log('Compra procesada');
    } else {
      console.log('Completa todos los campos correctamente');
    }
  }

  identifyCardNetwork(cardNumber: string): string {
    if (/^4/.test(cardNumber)) {
      return 'Visa';
    } else if (/^5/.test(cardNumber)) {
      return 'MasterCard';
    } else if (/^3[47]/.test(cardNumber)) {
      return 'American Express';
    } else {
      return 'Red de Tarjetas Desconocida';
    }
  }
}