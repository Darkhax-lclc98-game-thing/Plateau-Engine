VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = "lucid32"
  config.vm.box_url = "http://files.vagrantup.com/lucid32.box"

  $script = %Q{
    sudo apt-get update
    sudo apt-get install make build-essential zip doxygen libx11-dev xorg-dev libxi-dev libglew1.5 libglew1.5-dev freeglut3 freeglut3-dev cmake -y
												  
	cd /vagrant/libs/glfw-3.0.4
	sudo apt-get build-dep glfw -y
	cmake .
	make
	sudo make install
  }
  
  config.vm.provision :shell, :inline => $script
  
end