import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { DetailsComponent } from './profile/details/details.component';
import { IdpComponent } from './profile/idp/idp.component';
import { ProfileComponent } from './profile/profile.component';
import { SkillsComponent } from './profile/skills/skills.component';
import { ProjectComponent } from './profile/project/project.component';
import { SubdivisionComponent } from './profile/subdivision/subdivision.component';

const routes: Routes = [
  { 
    path: '', 
    component: HomeComponent
  },
  { 
    path: 'profile', 
    component: ProfileComponent,
    children: [
      {
        path: 'details',
        component: DetailsComponent 
      },
      {
        path: 'idp',
        component: IdpComponent 
      },
      {
        path: 'skills',
        component: SkillsComponent
      },
      {
        path: 'project',
        component: ProjectComponent
      },
      {
        path: 'subdivision',
        component: SubdivisionComponent
      }
    ]
  }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
