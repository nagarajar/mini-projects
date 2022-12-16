import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Contact } from './contact';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private http: HttpClient) { }

  private baseUrl = "http://localhost:9696/v1/app/contact";

  createContact(contact: Contact): Observable<any> {
    return this.http.post(`${this.baseUrl}/create`, contact, { responseType: 'text' });
  }

  getAllContacts(): Observable<Contact[]> {
    return this.http.get<Contact[]>(`${this.baseUrl}/all`);
  }

  getOneContact(id: number): Observable<Contact> {
    return this.http.get<Contact>(`${this.baseUrl}/find/${id}`);
  }

  updateContact(contact: Contact): Observable<any> {
    return this.http.put(`${this.baseUrl}/modify`, contact, { responseType: 'text' });
  }

  deleteContact(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/remove/${id}`, { responseType: 'text' });
  }

}
