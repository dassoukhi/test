import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BiblioSharedModule } from 'app/shared/shared.module';
import { LivreComponent } from './livre.component';
import { LivreDetailComponent } from './livre-detail.component';
import { LivreUpdateComponent } from './livre-update.component';
import { LivreDeleteDialogComponent } from './livre-delete-dialog.component';
import { livreRoute } from './livre.route';

@NgModule({
  imports: [BiblioSharedModule, RouterModule.forChild(livreRoute)],
  declarations: [LivreComponent, LivreDetailComponent, LivreUpdateComponent, LivreDeleteDialogComponent],
  entryComponents: [LivreDeleteDialogComponent],
})
export class BiblioLivreModule {}
