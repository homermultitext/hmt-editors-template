# hmt-editors-template


Template for creating editorial repositories to use in the Homer Multitext project virtual machine.

The current template is intended for use with the HMT project virtual machine used in summer, 2017.


The docs at <http://homermultitext.github.io/hmt-docs/vm/> are not yet up to date with this.

## Setting up a HMT project editorial repository

- install the [HMT project virtual machine](https://github.com/homermultitext/hmt-vm)
- start the VM (`vagrant up`), connect to the VM (`vagrant ssh`), and cd to the shared directory
(`cd /vagrant`)
- run `git clone https://github.com/homermultitext/hmt-editors-template.git`
- either in the VM or your host OS, rename the directory from `hmt-editors-template` to something for your project (e.g., "iliad14-venA")
- in the VM, run `bash adjustnames.sh`
