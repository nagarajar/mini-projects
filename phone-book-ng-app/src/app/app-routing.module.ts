import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactEditComponent } from './contact-edit/contact-edit.component';
import { ContactFormComponent } from './contactform/contact-form.component';
import { ContactsViewComponent } from './contacts-view/contacts-view.component';

const routes: Routes = [
  {path:'create', component:ContactFormComponent},
  {path:'all', component:ContactsViewComponent},
  {path:'edit/:id', component:ContactEditComponent},
  {path:'', redirectTo:'all',pathMatch:'full'},
  {path:'**', component:ContactFormComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
