# Within HMT VM, run HMT-MOM's palView task:

PALFILE=PATH/collections/paleography.csv

cd /vagrant/hmt-mom

gradle -Ppaleo=$PALFILE palView

