import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { GojsAngularModule } from 'gojs-angular';

import { AppRoutingModule } from './app-routing.module'; // CLI imports AppRoutingModule
import { AppComponent } from './app.component';
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';
import { StoreService } from './_services/store.service';
import { FormsModule } from '@angular/forms';
import { DetailsComponent } from './profile/details/details.component';
import { IdpComponent } from './profile/idp/idp.component';




@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    HomeComponent,
    DetailsComponent,
    IdpComponent
  ],
  imports: [
    GojsAngularModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, 
    NgxPaginationModule,
    FormsModule
  ],
  providers: [StoreService],
  bootstrap: [AppComponent]
})
export class AppModule { }