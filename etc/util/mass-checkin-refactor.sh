#!/bin/bash
for i in xchange-core xchange-*; do rsync -ahP --delete-after ../refactor/$i . ; git add $i;git commit -am "[${i/xchange-/}] large structure refactor operation #2432"  ;git tag -f "${i/-/_}_2432" ;done
