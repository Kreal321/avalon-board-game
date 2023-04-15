import { Pipe, PipeTransform } from '@angular/core';
import { Location } from '@angular/common';

@Pipe({name: 'isActive'})

export class PathIsActivePipe implements PipeTransform {

    constructor(private location: Location) {}
    
    transform(path: string): string {
        return this.location.path() === path ? 'active' : '';
    }
}