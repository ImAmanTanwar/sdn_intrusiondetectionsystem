echo "Creating Virtual environment......."
mkdir env
cd env
if virtualenv .  > /dev/null; then
	echo "Virtual Environment created."
else
	echo "Error creating Virtual Environment"
	em -rf env
	set -e
fi
source bin/activate
cd ..
echo "Installing Required Packages........"
if pip install -r Requirements.txt > /dev/null; then
	echo "Setup Complete."
else
	echo "Error Installing Packages."
	rm -rf env
fi
