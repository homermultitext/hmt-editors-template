#hmt-editors-template#


Template for creating editorial repositories to use in the Homer Multitext project virtual machine.


## Setting up a HMT project editorial repository

- install the [HMT project virtual machine](https://github.com/homermultitext/hmt-vm)
- start the VM (`vagrant up`), connect to the VM (`vagrant ssh`), and cd to the shared directory
(`cd /vagrant`)
- run `git clone https://github.com/homermultitext/hmt-editors-template.git`
- either in the VM or your host OS, rename the directory from `hmt-editors-template` to something for your project (e.g., "iliad14-venA")
- in the VM, run `bash adjustnames.sh`


More documentation to follow.  For a first glimpse at what you can do with this template, see the
documentation pages for the [HMT project 2014 summer seminar](http://www.homermultitext.org/summer2014/).
